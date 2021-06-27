package com.zgy.develop.net.netty.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();
        // netty自带解码器
        pipeline.addLast(new StringDecoder());
        // netty自带编码器
        pipeline.addLast(new StringEncoder());
        // 自定义handler
        pipeline.addLast(new ChatServerHandler());
    }
}
