package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 19:09
 * @description
 */
public abstract class AbstractGameFactory {
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group);
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);
    public abstract BaseExplosion createExplosion(int x, int y, TankFrame tankFrame);
}
