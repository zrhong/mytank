package com.mashibing.tank.entity;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/24 00:08
 * @description
 */
public class Bullet extends BaseBullet {

    private Dir dir;
    private final int speed = 10;
    public static final int width = ResourceMgr.bu.getWidth();
    public static final int height = ResourceMgr.bu.getHeight();
    private boolean alive = true;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        rectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        return rectangle;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    @Override
    public void paint(Graphics g) {
        if (!alive) {
            TankFrame.getInstance().bullets.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bl, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.br, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bu, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bd, x, y, null);
                break;
            default:
        }
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            default:
        }
        if (x < 0 || x > TankFrame.GAME_WIDTH || y < 0 || y > TankFrame.GAME_HEIGHT) {
            this.alive = false;
        }
    }

    @Override
    public void die() {
        alive = false;
    }
}
