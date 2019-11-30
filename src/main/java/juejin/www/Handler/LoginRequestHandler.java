package juejin.www.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.www.packet.LoginRequestPacket;
import juejin.www.packet.LoginResponsePacket;
import juejin.www.utils.LoginUtil;
import juejin.www.utils.Session;
import juejin.www.utils.SessionUtil;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        String userId = randomUserId();
        loginResponsePacket.setUserId(userId);
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());


        loginResponsePacket.setSuccess(true);//登录成功
        log.info("用户登录成功{},对应的userI为{}",loginRequestPacket.getUsername(),userId);
        SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUsername()),ctx.channel());
        LoginUtil.markAsLogin(ctx.channel());

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
