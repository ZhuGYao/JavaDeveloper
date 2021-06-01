package com.zgy.develop.net.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zgy
 * @data 2021/6/1 23:41
 */

@Slf4j
public class NioClient {

    public static void main(String[] args) throws Exception {

        // 创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 创建ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                log.info("连接需要等待....");
            }
        }

        String msg = "hello world";
        // wrap相当于 1.ByteBuffer byteBuffer = ByteBuffer.allocate(1024) 2.byteBuffer.put(str.getBytes())这两步
        ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(wrap);
        // 阻塞
        System.in.read();
    }
}
