package com.mashibing.tank.test.server.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/20 11:11
 * @description
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket();
            socket.bind(new InetSocketAddress("127.0.0.1",8888));
            while (true) {
                Socket accept = socket.accept();
                System.out.println("有客户端连上了");
                new Thread(() -> handle(accept)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handle(Socket accept) {
        int len = 0;
        InputStream inputStream = null;
        byte[] cache = new byte[1024];
        try {
            inputStream = accept.getInputStream();
            len = inputStream.read(cache);
            System.out.println(new String(cache, 0, len));
            accept.getOutputStream().write(cache, 0, len);
            accept.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
