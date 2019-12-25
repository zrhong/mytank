package com.mashibing.tank;

import java.awt.*;
import java.util.Random;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 22:55
 * @description
 */
public class Tank {
    private int x;
    private int y;
    private Dir dir = Dir.RIGHT;
    private final int speed = 5;
    private boolean moving = true;
    private TankFrame tankFrame;
    public static final int width = ResourceMgr.gtu.getWidth();
    public static final int height = ResourceMgr.gtu.getHeight();
    private boolean alive = true;
    private Group group = Group.BAD;
    private Random random = new Random();
    private Rectangle rectangle = new Rectangle(x, y, width, height);


    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        if (group.equals(Group.GOOD)) {
            moving = false;
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        if (!alive) {
            tankFrame.enemyTanks.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.gtl : ResourceMgr.btl, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.gtr : ResourceMgr.btr, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.gtu : ResourceMgr.btu, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.gtd : ResourceMgr.btd, x, y, null);
                break;
            default:
        }
        if (!moving) {
            return;
        }
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT:
                if (x - speed - Tank.width > 0) {
                    x -= speed;
                }
                break;
            case RIGHT:
                if (x + speed + Tank.width < TankFrame.GAME_WIDTH) {
                    x += speed;
                }
                break;
            case UP:
                if (y - speed - Tank.height > 0) {
                    y -= speed;
                }
                break;
            case DOWN:
                if (y + speed + Tank.height < TankFrame.GAME_HEIGHT) {
                    y += speed;
                }
                break;
            default:
        }

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            fire();
        }
        if (this.group == Group.BAD && random.nextInt(50) > 40) {
            randomDir();
        }
        boundsCheck();

    }

    private void boundsCheck() {
        if (x < 2) {
            x = 2;
        }
        if (x > TankFrame.GAME_WIDTH - Tank.width - 2) {
            x = TankFrame.GAME_WIDTH - Tank.width - 2;
        }
        if (y < 28) {
            y = 28;
        }
        if (y > TankFrame.GAME_HEIGHT - Tank.height - 2) {
            y = TankFrame.GAME_HEIGHT - Tank.height - 2;
        }
    }

    private void randomDir() {
        dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
//        int bx = this.x + this.width / 2 ;
//        int by = this.y + this.height / 2;
//        tankFrame.bullets.add(new Bullet(bx, by, this.dir, this.tankFrame));
        switch (dir) {
            case RIGHT:
                tankFrame.bullets.add(new Bullet(this.x + 65, this.y + 15, this.dir, this.group, this.tankFrame));
                break;
            case LEFT:
                tankFrame.bullets.add(new Bullet(this.x - 30, this.y + 15, this.dir, this.group, this.tankFrame));
                break;
            case UP:
                tankFrame.bullets.add(new Bullet(this.x + 15, this.y - 30, this.dir, this.group, this.tankFrame));
                break;
            case DOWN:
                tankFrame.bullets.add(new Bullet(this.x + 15, this.y + 65, this.dir, this.group, this.tankFrame));
                break;
            default:

        }
    }

    public void die() {
        alive = false;
        tankFrame.explosions.add(new Explosion(x, y, tankFrame));
    }
}
