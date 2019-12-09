package com.mashibing.tank.chainofresponsibility;

import com.mashibing.tank.entity.Bullet;
import com.mashibing.tank.entity.Tank;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/9 17:36
 * @description
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collision(Object o1, Object o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;
            if (bullet.getGroup().equals(tank.getGroup())) {
                return false;
            }
            //优化
            Rectangle rect1 = bullet.getRectangle();
            Rectangle rect2 = tank.getRectangle();
            if (rect1.intersects(rect2)) {
                bullet.die();
                tank.die();
                return true;
            }
        }
        if (o1 instanceof Tank && o2 instanceof Bullet) {
            collision(o2, o1);
        }
        return false;
    }
}
