package com.zgy.develop.net.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuffer {

    public static void main(String[] args) {

//        test1();
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

    }
}
