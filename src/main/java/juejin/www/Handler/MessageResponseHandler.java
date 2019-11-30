package juejin.www.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.www.packet.MessageResponsePacket;
import juejin.www.utils.Session;
import juejin.www.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {

        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();

        System.out.println("收到来自【"+fromUserId + ":" + fromUserName + " 】的消息 " + messageResponsePacket.getMessage());
    }
}
