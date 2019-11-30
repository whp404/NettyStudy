package juejin.www.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import juejin.www.Handler.*;
import juejin.www.command.ConsoleCommandManager;
import juejin.www.command.LoginConsoleCommand;
import juejin.www.packet.LoginRequestPacket;
import juejin.www.packet.MessageRequestPacket;
import juejin.www.packet.PacketCodeC;
import juejin.www.utils.LoginUtil;
import juejin.www.utils.SessionUtil;
import juejin.www.utils.Spliter;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new ClientHandler());
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4));
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecodeHandler());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        //ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());

                        ch.pipeline().addLast(new MessageToByteEncoderHandler());
                    }
                });

        connect(bootstrap,HOST,PORT,MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功!");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);

            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    private static void startConsoleThread(Channel channel){
        Scanner scanner = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        new Thread(()->{
            while(!Thread.interrupted()){
//                if(LoginUtil.hasLogin(channel)){
//                    System.out.println("输入消息发送至服务端: ");
//                    String toUserId = sc.next();
//                    String message = sc.next();
//                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
//                }else{
//                    System.out.println("输入用户名密码到服务端登录: ");
//                    String userName = sc.nextLine();
//
//                    LoginRequestPacket packet = new LoginRequestPacket();
//                    packet.setUsername(userName);
//
//                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
//                    channel.writeAndFlush(byteBuf);
//
//                    waitForLoginResponse();//这行代码不可以缺少,否则还没有等到登录成功的回复，又去登录了，会造成逻辑混乱，我觉得实际生产中，应等待一段时间，如果没有登录成功，才会再等陆
//                }

                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }

        }).start();
    }

}
