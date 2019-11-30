package juejin.www.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.www.packet.LoginRequestPacket;
import juejin.www.packet.LoginResponsePacket;
import juejin.www.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("flash");
//        loginRequestPacket.setPassword("pwd");
//
//        // 写数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            log.info("客户端用户名{}登录成功",loginResponsePacket.getUserName());
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}