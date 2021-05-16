package com.zgy.develop.net.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zgy
 * @data 2021/5/17 0:36
 */

@Slf4j
public class BioServer {

    public static void main(String[] args) throws Exception {

        // 定义线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 开启服务
        ServerSocket serverSocket = new ServerSocket(9999);
        log.info("server start.....");
        // 循环监听
        while (true) {
            // 等待客户端连接
            Socket accept = serverSocket.accept();
            log.info("get connection....");
            // 提交线程池
            executorService.execute(() -> socketHandler(accept));
        }
    }

    /**
     * 和客户端通信方法
     * @param socket
     */
    public static void socketHandler(Socket socket) {

        byte[] bytes = new byte[1024];
        try {
            // 获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取
            while (true) {
                if (inputStream.read(bytes) != -1) {
                    // 输出
                    log.info(Thread.currentThread().getName() + ":===>" + new String(bytes, 0, bytes.length - 1));
                } else {
                    // 通信结束
                    break;
                }
            }
        } catch (Exception e) {
            log.error("connection error");
            e.printStackTrace();
        } finally {
            try {
                log.error("connection close");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
