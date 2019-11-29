package juejin.www.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.www.packet.LoginRequestPacket;
import juejin.www.packet.LoginResponsePacket;
import juejin.www.utils.Session;
import juejin.www.utils.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        String userId = randomUserId();
        loginResponsePacket.setUserId(userId);
        SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());

//        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
//        if (valid(loginRequestPacket)) {
//            loginResponsePacket.setSuccess(true);
//            System.out.println(new Date() + ": 登录成功!");
//        } else {
//            loginResponsePacket.setReason("账号密码校验失败");
//            loginResponsePacket.setSuccess(false);
//            System.out.println(new Date() + ": 登录失败!");
//        }

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
