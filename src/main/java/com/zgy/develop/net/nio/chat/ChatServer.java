package com.zgy.develop.net.nio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zgy
 * @data 2021/6/2 0:45
 */

@Slf4j
public class ChatServer {

    private Selector selector;

    private ServerSocketChannel listenSocketChannel;

    private static final int PORT = 7777;

    private Map<Integer, SocketChannel> clientMap;

    public ChatServer () {

        try {
            // 初始化选择器
            selector = Selector.open();
            listenSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            listenSocketChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenSocketChannel.configureBlocking(false);
            // 注册
            listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            clientMap = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {

            for (;;) {

                if (selector.select(2000) <= 0) {
                    continue;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = listenSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        log.info("{}:上线了", socketChannel.getRemoteAddress());

                        clientMap.put(socketChannel.hashCode(), socketChannel);
                    }

                    if (selectionKey.isReadable()) {
                        readData(selectionKey);
                    }

                    // 移除,防止重复
                    iterator.remove();
                }
            }
        } catch (IOException e) {

        } finally {

        }
    }

    private void readData(SelectionKey selectionKey) {

        SocketChannel socketChannel = null;
        try {
            // 获取channel
            socketChannel = (SocketChannel) selectionKey.channel();
            // 获取buffer
            ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
            // 读取
            int count = socketChannel.read(byteBuffer);
            // 如果读取到消息
            if (count > 0) {
                // 输出
                log.info("{}:{}",socketChannel.getRemoteAddress(),new String(byteBuffer.array()));
                // 循环发送
                for (Integer key : clientMap.keySet()) {
                    if (socketChannel.hashCode() != key) {
                        clientMap.get(key).write(byteBuffer);
                    }
                }
            }

        } catch (IOException e) {

        }

    }

    public static void main(String[] args) {

    }
}
