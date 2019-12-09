package com.mashibing.tank;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 19:46
 * @description
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame frame = TankFrame.getInstance();
        frame.setLocation(500, 30);
        frame.setAlwaysOnTop(true);
        while (true) {
            Thread.sleep(50);
            frame.repaint();
        }
    }
}
