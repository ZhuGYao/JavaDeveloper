package com.zgy.develop.net.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyByteBuffer {

    public static void main(String[] args) {

        test2();
    }

    public static void test1() {

        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.writeByte(i);
        }

        // 无需flip()
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }

    public static void test2() {

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            log.info(new String(array, CharsetUtil.UTF_8));
        }
    }
}
