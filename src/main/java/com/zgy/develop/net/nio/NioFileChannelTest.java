package com.zgy.develop.net.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zgy
 * @data 2021/5/17 23:25
 */

public class NioFileChannelTest {

    public static void main(String[] args) throws Exception {

//        String str = "hello,world";
//
//        // 获取输出流
//        FileOutputStream fileOutputStream = new FileOutputStream("test1.txt");
//        // 得到channel
//        FileChannel channel = fileOutputStream.getChannel();
//
//        // 创建buffer
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//        byteBuffer.put(str.getBytes());
//
//        // 进行反转
//        byteBuffer.flip();
//
//        // 写入
//        channel.write(byteBuffer);
//
//        // 关闭流
//        fileOutputStream.close();

        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("test1.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("test2.txt");
        FileChannel outputChannel = fileOutputStream.getChannel();

        // 进行拷贝
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        // 关闭流
        outputChannel.close();
        inputChannel.close();
        fileOutputStream.close();
        fileInputStream.close();
    }
}
