package com.mashibing.tank;

import com.mashibing.tank.net.Client;
import com.mashibing.tank.net.msg.BulletNewMsg;
import com.mashibing.tank.net.msg.TankJoinMsg;
import lombok.Data;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 22:55
 * @description
 */
@Data
public class Tank {
    private int x;
    private int y;
    private Dir dir = Dir.RIGHT;
    private final int speed = 2;
    private boolean moving = true;
    private TankFrame tankFrame = TankFrame.INSTANCE;
    public static final int width = ResourceMgr.gtu.getWidth();
    public static final int height = ResourceMgr.gtu.getHeight();
    private boolean alive = true;
    private Group group = Group.GOOD;
    private Random random = new Random();
    private Rectangle rectangle = new Rectangle(x, y, width, height);

    private UUID id = UUID.randomUUID();

    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.moving = msg.isMoving();
//        this.group = msg.group;
        this.id = msg.getId();

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = width;
        rectangle.height = height;
    }


    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        return rectangle;
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

    public void paint(Graphics g) {
        if (!alive) {
            return;
        }
        //uuid on head
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), this.x, this.y - 20);
        g.drawString("live=" + alive, x, y - 10);
        g.setColor(c);
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

    /**
     * 边界检测
     */
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

    /**
     * 坦克发射炮弹
     */
    public void fire() {
        if (Group.GOOD.equals(group)) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
        int bX = x + Tank.width / 2 - Bullet.width / 2;
        int bY = y + Tank.height / 2 - Bullet.height / 2;
//        switch (dir) {
//            case RIGHT:
//                tankFrame.bullets.add(new Bullet(this.x + 65, this.y + 15, this.dir, this.group, this.tankFrame));
//                break;
//            case LEFT:
//                tankFrame.bullets.add(new Bullet(this.x - 30, this.y + 15, this.dir, this.group, this.tankFrame));
//                break;
//            case UP:
//                tankFrame.bullets.add(new Bullet(this.x + 15, this.y - 30, this.dir, this.group, this.tankFrame));
//                break;
//            case DOWN:
//                break;
//            default:
//                tankFrame.bullets.add(new Bullet(this.x + 15, this.y + 65, this.dir, this.group, this.tankFrame));
//
//        }
        Bullet bullet = new Bullet(id, bX, bY, this.dir, this.tankFrame);
        tankFrame.bullets.add(bullet);
        Client.INSTANCE.send(new BulletNewMsg(bullet));
    }

    /**
     * 挂了
     */
    public void die() {
        alive = false;
        tankFrame.explosions.add(new Explosion(x, y, tankFrame));
    }
}
