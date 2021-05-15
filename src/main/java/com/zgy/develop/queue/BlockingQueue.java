package com.zgy.develop.queue;

import java.util.LinkedList;

/**
 * @author zgy
 * @data 2021/5/15 22:28
 */

public class BlockingQueue<T> {

    // 模拟队列
    private final LinkedList<T> queue = new LinkedList<>();

    private int MAX_SIZE = 1;
    private int count = 0;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("size最小为1");
        }
        this.MAX_SIZE = capacity;
    }

    public synchronized void put(T resource) throws InterruptedException {
        while (queue.size() >= MAX_SIZE) {
            // 队列满了，不能再塞东西了，阻塞生产者
            System.out.println("插入阻塞...");
            this.wait();
        }
        queue.addFirst(resource);
        count++;
        printMsg(resource, "被插入");
        this.notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (queue.size() <= 0) {
            // 队列空了，不能再取东西了，阻塞消费者
            System.out.println("取出阻塞...");
            this.wait();
        }
        T resource = queue.removeLast();
        count--;
        printMsg(resource, "被取出");
        this.notifyAll();
        return resource;
    }

    private void printMsg(T resource, String operation) throws InterruptedException {
        System.out.println(resource + operation);
        System.out.println("队列容量：" + count);
    }
}
