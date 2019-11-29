package com.mashibing.tank.entity;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.abstractfactory.BaseTank;
import com.mashibing.tank.abstractfactory.FourDirFactory;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.strategy.DefaultFireStrategy;
import com.mashibing.tank.strategy.FireStrategy;
import com.mashibing.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 22:55
 * @description
 */
public class Tank1st extends BaseTank {
    private final int speed = 5;
    private TankFrame tankFrame;
    public static final int width = ResourceMgr.gtu.getWidth();
    public static final int height = ResourceMgr.gtu.getHeight();
    private boolean alive = true;

    private Random random = new Random();


    @Override
    public Rectangle getRectangle() {
        rectangle.x = this.x;
        rectangle.y = this.y;
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Tank1st(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        rectangle = new Rectangle(x, y, width, height);
        if (group.equals(Group.GOOD)) {
            moving = false;

        }
    }
    @Override
    public void paint(Graphics g) {
        if (!alive) {
            tankFrame.enemyTanks.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.btl, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.btr, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.btu, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.btd, x, y, null);
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
                if (x - speed - Tank1st.width > 0) {
                    x -= speed;
                }
                break;
            case RIGHT:
                if (x + speed + Tank1st.width < TankFrame.GAME_WIDTH) {
                    x += speed;
                }
                break;
            case UP:
                if (y - speed - Tank1st.height > 0) {
                    y -= speed;
                }
                break;
            case DOWN:
                if (y + speed + Tank1st.height < TankFrame.GAME_HEIGHT) {
                    y += speed;
                }
                break;
            default:
        }

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            fire(DefaultFireStrategy.getInstance());
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
        if (x > TankFrame.GAME_WIDTH - Tank1st.width - 2) {
            x = TankFrame.GAME_WIDTH - Tank1st.width - 2;
        }
        if (y < 28) {
            y = 28;
        }
        if (y > TankFrame.GAME_HEIGHT - Tank1st.height - 2) {
            y = TankFrame.GAME_HEIGHT - Tank1st.height - 2;
        }
    }

    private void randomDir() {
        dir = Dir.values()[random.nextInt(4)];
    }

    @Override
    public void fire(FireStrategy strategy) {
        strategy.fire(this);
    }

    @Override
    public void die() {
        alive = false;
        tankFrame.explosions.add(FourDirFactory.getInstance().createExplosion(x, y, tankFrame));
    }
}
