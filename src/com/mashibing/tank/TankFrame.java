package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 19:59
 * @description
 */

public class TankFrame extends Frame {
    private Tank myTank = new Tank(20, 300, Dir.RIGHT, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    public List<Tank> enemyTanks = new ArrayList<>();
    public List<Explosion> explosions = new ArrayList<>();
    //    Bullet bullet = new Bullet(30, 30, Dir.DOWN);
    public static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setVisible(true);
        setTitle("tank war");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyAdapter() {
            boolean bl = false;
            boolean br = false;
            boolean bu = false;
            boolean bd = false;

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        bl = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        br = true;
                        break;
                    case KeyEvent.VK_UP:
                        bu = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        bd = true;
                        break;
                    default:
                }
                setTankDir();
            }

            private void setTankDir() {
                if (!bl && !br && !bu && !bd) {
                    myTank.setMoving(false);
                } else {
                    myTank.setMoving(true);
                    if (bl) {
                        myTank.setDir(Dir.LEFT);
                    }
                    if (br) {
                        myTank.setDir(Dir.RIGHT);
                    }
                    if (bu) {
                        myTank.setDir(Dir.UP);
                    }
                    if (bd) {
                        myTank.setDir(Dir.DOWN);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        bl = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        br = false;
                        break;
                    case KeyEvent.VK_UP:
                        bu = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        bd = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        myTank.fire();
                        break;
                    default:
                }
                setTankDir();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.white);
        g.drawString("子弹的数量:" + bullets.size(), 5, 50);
        g.drawString("敌人的数量:" + enemyTanks.size(), 5, 70);
        g.drawString("爆炸的数量:" + explosions.size(), 5, 90);
        g.setColor(color);
        myTank.paint(g);
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
        for (int i = 0; i <explosions.size() ; i++) {
            explosions.get(i).paint(g);
        }
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

    Image offScreenImage = null;

    /**
     * 使用双缓冲解决屏幕闪烁问题
     *
     * @param g
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}
