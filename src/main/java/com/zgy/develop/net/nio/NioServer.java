package com.zgy.develop.net.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zgy
 * @data 2021/6/1 22:37
 */

@Slf4j
public class NioServer {

    public static void main(String[] args) throws Exception {

        // 获取serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 获取selector
        Selector selector = Selector.open();

        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 将serverSocketChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待
        for (;;) {

            // 阻塞等待
            if (selector.select(1000) == 0) {
                log.info("服务器等待1秒钟");
                continue;
            }

            // 如果>0,表示事件发生
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                // 如果是SelectionKey.OP_ACCEPT事件
                if (selectionKey.isAcceptable()) {
                    // 因为已经发生事件了，所以此时直接调用accept就可以
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 注册到selector上，关注读事件
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                // 如果是SelectionKey.OP_READ事件
                if (selectionKey.isReadable()) {
                    // 根据key获取到对应Channel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 获取到提前绑定的ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel.read(byteBuffer);

                    log.info("客户端发送数据为：{}", new String(byteBuffer.array()));
                }

                // 手动移除，防止重复接收
                keyIterator.remove();
            }

        }
    }
}
