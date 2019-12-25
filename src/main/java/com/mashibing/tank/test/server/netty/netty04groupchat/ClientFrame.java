package com.mashibing.tank.test.server.netty.netty04groupchat;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/23 21:14
 * @description
 */
public class ClientFrame extends Frame {
    public TextArea ta = new TextArea();
    public TextField tf = new TextField();

    private Client client = null;

    private static final ClientFrame INSTANCE = new ClientFrame();

    private ClientFrame() {
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        tf.addActionListener(e -> {
            //把字符串发送到服务器
//                ta.setText(ta.getText() + tf.getText() + "\r\n");
            client.sendMsg(tf.getText());
            tf.setText("");
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);


    }

    public static ClientFrame getInstance() {
        return INSTANCE;
    }

    private void connect2Server(){
        client = new Client();
        client.connect();
    }
    public void updateMsg(String msg) {
        ta.setText(ta.getText() + System.getProperty("line.separator") + msg);
    }

    public static void main(String[] args) {
        ClientFrame.INSTANCE.connect2Server();
    }
}