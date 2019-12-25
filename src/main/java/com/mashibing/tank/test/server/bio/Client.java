package com.mashibing.tank.test.server.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/20 11:35
 * @description
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8889);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello server".getBytes("utf-8"));
            outputStream.flush();
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            System.out.println(new String(bytes, 0, len));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
