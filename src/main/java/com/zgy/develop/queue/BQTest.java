package com.zgy.develop.queue;

import lombok.SneakyThrows;

/**
 * @author zgy
 * @data 2021/5/15 22:34
 */

public class BQTest {

    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new BlockingQueue<>(10);

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    blockingQueue.put(i + "");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 30; i < 60; i++) {
                    blockingQueue.put(i + "");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (;;) blockingQueue.take();
            }
        }).start();
    }
}
