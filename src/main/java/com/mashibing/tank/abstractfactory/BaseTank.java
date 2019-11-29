package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.strategy.FireStrategy;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 19:13
 * @description
 */
public abstract class BaseTank {
    public int x;
    public int y;
    public Dir dir;
    public boolean moving = true;
    public Group group;
    public Rectangle rectangle;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public abstract void paint(Graphics g);

    public abstract void fire(FireStrategy instance);

    public abstract void die();
}
