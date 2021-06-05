package com.zgy.develop.net.nio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author zgy
 * @data 2021/6/2 0:45
 */

@Slf4j
public class ChatServer {

    private Selector selector;

    private Selector slaveSelector;

    private ServerSocketChannel listenSocketChannel;

    private static final int PORT = 7777;

    public ChatServer () {

        try {
            // 初始化选择器
            selector = Selector.open();
            // 初始化从选择器
            slaveSelector = Selector.open();
            listenSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            listenSocketChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenSocketChannel.configureBlocking(false);
            // 注册
            listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {

            for (;;) {

                if (selector.select(2000) > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenSocketChannel.accept();
                            String msg = socketChannel.getRemoteAddress().toString().substring(1) + ":" + "上线了...";
                            socketChannel.configureBlocking(false);
                            // 将读请求都注册到从选择器上
                            socketChannel.register(slaveSelector, SelectionKey.OP_READ);
                            log.info("{}:上线了", socketChannel.getRemoteAddress());
                            sendAllUser(msg, socketChannel);
                        }
                        // 移除,防止重复
                        iterator.remove();
                    }
                }

                if (slaveSelector.select(2000) > 0) {
                    Iterator<SelectionKey> iterator = slaveSelector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        // 移除,防止重复
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void readData(SelectionKey selectionKey) {

        SocketChannel socketChannel = null;
        try {
            // 获取channel
            socketChannel = (SocketChannel) selectionKey.channel();
            // 获取buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 读取
            int count = socketChannel.read(byteBuffer);
            // 如果读取到消息
            if (count > 0) {

                String msg = new String(byteBuffer.array());
                // 输出
                log.info("{}:{}",socketChannel.getRemoteAddress(), msg);
                sendAllUser(msg, socketChannel);
            }

        } catch (IOException e) {
            try {
                String msg = socketChannel.getRemoteAddress().toString().substring(1) + ":" + "离线了...";
                log.info(msg);
                sendAllUser(msg, socketChannel);
                socketChannel.close();
                selectionKey.cancel();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

    public void sendAllUser(String msg, SocketChannel self) throws IOException {
        Set<SelectionKey> keys = slaveSelector.keys();
        // 循环发送
        for (SelectionKey key : keys) {
            Channel channel = key.channel();
            if (channel instanceof  SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel) channel;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listen();
    }
}
