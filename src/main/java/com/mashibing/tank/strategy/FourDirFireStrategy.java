package com.mashibing.tank.strategy;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.entity.Bullet;
import com.mashibing.tank.entity.Tank;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:28
 * @description
 */
public class FourDirFireStrategy implements FireStrategy{
    private FourDirFireStrategy(){}

    private static class FourDirFireStrategyHolder{
        public static final FourDirFireStrategy INSTANCE = new FourDirFireStrategy();
    }

    public static FourDirFireStrategy getInstance() {
        return FourDirFireStrategyHolder.INSTANCE;
    }

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.width / 2 - Bullet.width / 2;
        int bY = tank.getY() + Tank.height / 2 - Bullet.height / 2;
        for (Dir dir : Dir.values()) {
            TankFrame.getInstance().bullets.add(new Bullet(bX, bY, dir, tank.getGroup()));
        }
    }
}
