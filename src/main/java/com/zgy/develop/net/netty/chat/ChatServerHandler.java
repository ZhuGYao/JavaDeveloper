package com.zgy.develop.net.netty.chat;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.pipeline().channel();
        // 群发通知
        channelGroup.writeAndFlush("[客户端]：" + channel.remoteAddress() + "加入聊天室\n");
        // 新来的handler加入
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.pipeline().channel();
        // 删除handler
        channelGroup.remove(channel);
        // 群发通知
        channelGroup.writeAndFlush("[客户端]：" + channel.remoteAddress() + "推出聊天室\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {


    }
}
