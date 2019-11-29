package com.mashibing.tank;

import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.entity.Tank;
import com.mashibing.tank.util.PropertyMgr;

import java.util.Random;

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
        int initTankCount = Integer.valueOf((String) PropertyMgr.getProp("initTankCount"));
        while (true) {
            Thread.sleep(50);
            if (frame.enemyTanks.size() < initTankCount) {
                frame.enemyTanks.add(new Tank(TankFrame.GAME_WIDTH - 50, new Random().nextInt(TankFrame.GAME_HEIGHT - 50), Dir.LEFT, Group.BAD, frame));
            }
            frame.repaint();
        }
    }
}
