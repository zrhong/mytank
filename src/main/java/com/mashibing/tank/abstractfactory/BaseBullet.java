package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.constant.Group;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 19:12
 * @description
 */
public abstract class BaseBullet {
    public int x;
    public int y;
    public Group group;
    public Rectangle rectangle;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public abstract void paint(Graphics g);

    public abstract void die();
}
