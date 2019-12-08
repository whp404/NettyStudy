package juejin.www.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 客户端的台控制命令优化
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
