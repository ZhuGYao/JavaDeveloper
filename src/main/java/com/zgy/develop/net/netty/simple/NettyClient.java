package com.zgy.develop.net.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zgy
 * @data 2021/6/5 23:56
 */

public class NettyClient {

    public static void main(String[] args) throws Exception{
        // 创建客户端时间循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        // 创建客户端启动器
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            // 对关闭通道事件进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
