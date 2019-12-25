package com.mashibing.tank;

import com.mashibing.tank.net.Client;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 19:46
 * @description
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame frame = TankFrame.INSTANCE;
        frame.setLocation(500, 30);
//        int initTankCount = Integer.valueOf((String) PropertyMgr.getProp("initTankCount"));
//music
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        //ui线程独立开，省的阻塞主线程
        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.repaint();
            }
        }).start();
        //连接服务器 发送加入消息
        Client.INSTANCE.connect();

    }
}
