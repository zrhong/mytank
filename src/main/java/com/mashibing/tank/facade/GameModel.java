package com.mashibing.tank.facade;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.entity.Bullet;
import com.mashibing.tank.entity.Explosion;
import com.mashibing.tank.entity.Tank;
import com.mashibing.tank.util.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/9 12:23
 * @description
 */
public class GameModel {
    private static final GameModel INSTANCE = new GameModel();
    public Tank myTank = new Tank(20, 200, Dir.RIGHT, Group.GOOD);
    public List<Bullet> bullets = new ArrayList<>();
    public List<Tank> enemyTanks = new ArrayList<>();
    public List<Explosion> explosions = new ArrayList<>();

    private GameModel() {
        int initTankCount = Integer.valueOf((String) PropertyMgr.getProp("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            enemyTanks.add(new Tank(TankFrame.GAME_WIDTH - 50, new Random().nextInt(TankFrame.GAME_HEIGHT - 50), Dir.LEFT, Group.BAD));
        }
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.white);
        g.drawString("子弹的数量:" + bullets.size(), 5, 50);
        g.drawString("敌人的数量:" + enemyTanks.size(), 5, 70);
        g.drawString("爆炸的数量:" + explosions.size(), 5, 90);
        g.setColor(color);
        if (myTank != null) {
            myTank.paint(g);
        }
        //迭代器只能在迭代的地方删除，不能其他地方删除，不然会报错  java.util.ConcurrentModificationException
//        for (Bullet b:bullets) {
//            b.paint(g);
//        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).paint(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemyTanks.size(); j++) {
                intersectDeal(bullets.get(i), enemyTanks.get(j));
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }
    }

    public Tank getMytank() {
        return myTank;
    }

    private void intersectDeal(Bullet bullet, Tank tank) {
        if (bullet.getGroup().equals(tank.getGroup())) {
            return;
        }
        //优化
        Rectangle rect1 = bullet.getRectangle();
        Rectangle rect2 = tank.getRectangle();
//        Rectangle rect1 = new Rectangle(bullet.getX(), bullet.getY(), Bullet.width, Bullet.height);
//        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.width, Tank.height);
        if (rect1.intersects(rect2)) {
            bullet.die();
            tank.die();
        }
    }
}

