package juejin.www.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.www.packet.MessageRequestPacket;
import juejin.www.packet.MessageResponsePacket;
import juejin.www.utils.Session;
import juejin.www.utils.SessionUtil;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //接受者的连接
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());


        // 4.将消息发送给消息接收方 ，说明接收者在线
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }


        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
