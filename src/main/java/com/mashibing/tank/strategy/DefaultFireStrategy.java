package com.mashibing.tank.strategy;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.entity.Bullet;
import com.mashibing.tank.entity.Tank;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:03
 * @description
 */
public class DefaultFireStrategy implements FireStrategy {
    private DefaultFireStrategy() {
    }

    private static class DefaultFireStrategyHolder {
        private static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }

    public static DefaultFireStrategy getInstance() {
        return DefaultFireStrategyHolder.INSTANCE;
    }

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.width / 2 - Bullet.width / 2;
        int bY = tank.getY() + Tank.height / 2 - Bullet.height / 2;
        TankFrame.getInstance().bullets.add(new Bullet(bX, bY, tank.dir, tank.getGroup()));
    }
}
