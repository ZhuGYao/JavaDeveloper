package com.zgy.develop.net.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zgy
 * @data 2021/6/8 23:30
 */

@Slf4j
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 向管道加入处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        // netty 提供的http编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

    }
}
