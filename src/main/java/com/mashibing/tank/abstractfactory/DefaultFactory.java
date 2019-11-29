package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.entity.Bullet1st;
import com.mashibing.tank.entity.Explosion1st;
import com.mashibing.tank.entity.Tank1st;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 19:15
 * @description
 */
public class DefaultFactory extends AbstractGameFactory {
    private static final DefaultFactory instance = new DefaultFactory();

    public static DefaultFactory getInstance() {
        return instance;
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new Bullet1st(x, y, dir, group);
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank1st(x, y, dir, group, tf);
    }

    @Override
    public BaseExplosion createExplosion(int x, int y, TankFrame tankFrame) {
        return new Explosion1st(x, y, tankFrame);
    }
}
