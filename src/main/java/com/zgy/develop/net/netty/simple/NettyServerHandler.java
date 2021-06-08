package com.zgy.develop.net.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zgy
 * @data 2021/6/5 22:09
 */

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("客户端->{}:{}",ctx.channel().remoteAddress(), buf.toString(CharsetUtil.UTF_8));

        /**
         * 耗时长的业务
         */
//        Thread.sleep(10000);
//        String str = "hello world1";
//        ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
        // 解决方案一
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10000);
                String str = "hello world1";
                ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
            } catch (Exception e) {
                log.error("error : {}", e.getMessage());
            }
        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        String msg = "hello world2";
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
