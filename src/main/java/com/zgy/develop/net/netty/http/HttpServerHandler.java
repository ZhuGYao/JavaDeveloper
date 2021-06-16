package com.zgy.develop.net.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author zgy
 * @data 2021/6/8 22:59
 */

@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            log.info("client address : {}", ctx.channel().remoteAddress());

            // 根据uri过滤特定资源
            HttpRequest hr = (HttpRequest) msg;
            URI uri = new URI(hr.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                log.info("favicon request : {}", uri.getPath());
                return;
            }
            // 返回内容
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            // 设置请求头
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            // 写回客户端
            ctx.writeAndFlush(httpResponse);
        }
    }
}
