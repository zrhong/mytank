package com.mashibing.tank;

import java.awt.*;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/24 00:08
 * @description
 */
public class Bullet {
    private int x;
    private int y;
    private Dir dir;
    private final int speed = 10;
    public static final int width = ResourceMgr.bu.getWidth();
    public static final int height = ResourceMgr.bu.getHeight();
    private boolean alive = true;
    private TankFrame tankFrame;
    private Group group;
    public Rectangle rectangle = new Rectangle(x,y, width, height);
    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }

    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        return rectangle;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!alive) {
            tankFrame.bullets.remove(this);
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

    public void die() {
        alive = false;
    }
}
