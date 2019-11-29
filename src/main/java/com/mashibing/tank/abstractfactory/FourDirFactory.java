package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.entity.Bullet;
import com.mashibing.tank.entity.Explosion;
import com.mashibing.tank.entity.Tank;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 19:16
 * @description
 */
public class FourDirFactory extends AbstractGameFactory {
    private static final FourDirFactory instance = new FourDirFactory();
    public static FourDirFactory getInstance(){
        return instance;
    }
    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new Bullet(x, y, dir, group);
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank(x,y, dir, group,tf);
    }

    @Override
    public BaseExplosion createExplosion(int x, int y, TankFrame tankFrame) {
        return new Explosion(x,y,tankFrame);
    }
}
