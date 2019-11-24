package com.mashibing.tank;

import java.awt.*;
import java.util.Random;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 22:55
 * @description
 */
public class Explosion {
    private int x;
    private int y;
    private TankFrame tankFrame;
    public static final int width = ResourceMgr.explosions[0].getWidth();
    public static final int height = ResourceMgr.explosions[0].getHeight();
    private boolean alive = true;
    int step = 0;

    public Explosion(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
//
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explosions[step++], x, y, null);
        if (step >= ResourceMgr.explosions.length) {
            tankFrame.explosions.remove(this);
        }
    }

}
