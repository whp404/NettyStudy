package juejin.www.command;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import juejin.www.packet.LoginRequestPacket;
import juejin.www.packet.PacketCodeC;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名密码到服务端登录: ");
        String userName = scanner.nextLine();

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername(userName);

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
        channel.writeAndFlush(byteBuf);

        waitForLoginResponse();//这行代码不可以缺少,否则还没有等到登录成功的回复，又去登录了，会造成逻辑混乱，我觉得实际生产中，应等待一段时间，如果没有登录成功，才会再等陆
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
    }
}
