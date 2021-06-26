package com.zgy.develop.net.netty.chat;

public class ChatServer {

    private int defaultPort = 8080;

    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public ChatServer() {
        new ChatServer(this.defaultPort);
    }

    public static void main(String[] args) {

    }
}
