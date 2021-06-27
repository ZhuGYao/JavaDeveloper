package com.zgy.develop.net.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {

    private final int DEFAULT_PORT = 8080;

    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public ChatServer() {
        new ChatServer(this.DEFAULT_PORT);
    }

    public void run() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try{
            ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChatServerInitializer());

            // 绑定端口同步
            ChannelFuture cf = bootstrap.bind(this.port).sync();
            // 对关闭通道事件进行监听
            cf.channel().closeFuture().sync();
        } finally {
            // 关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception{
        new ChatServer(8888).run();
    }
}
