package com.mashibing.tank.entity;

import com.mashibing.tank.Audio;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.util.ResourceMgr;
import lombok.Data;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 22:55
 * @description
 */
@Data
public class Explosion {
    private int x;
    private int y;
    private TankFrame tankFrame;
    public static final int width = ResourceMgr.explosions[0].getWidth();
    public static final int height = ResourceMgr.explosions[0].getHeight();
    int step = 0;

    public Explosion(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
//
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explosions[step++], x, y, null);
        if (step >= ResourceMgr.explosions.length) {
            tankFrame.explosions.remove(this);
        }
    }

}
