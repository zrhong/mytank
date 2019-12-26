package com.mashibing.tank;

import com.mashibing.tank.net.Client;
import com.mashibing.tank.net.msg.TankDieMsg;
import com.mashibing.tank.net.msg.TankDirChangedMsg;
import com.mashibing.tank.net.msg.TankStartMovingMsg;
import com.mashibing.tank.net.msg.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 19:59
 * @description
 */

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();
    Random r = new Random();
    private Tank myTank = new Tank(r.nextInt(GAME_WIDTH), r.nextInt(GAME_HEIGHT), Dir.RIGHT, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    public Map<UUID, Tank> tanks = new HashMap<>();
    //    public List<Tank> enemyTanks = new ArrayList<>();
    public List<Explosion> explosions = new ArrayList<>();
    //    Bullet bullet = new Bullet(30, 30, Dir.DOWN);
    public static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;

    private TankFrame() {
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
                new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
            }

            private void setTankDir() {
                Dir oldDir = myTank.getDir();
                if (!bl && !br && !bu && !bd) {
                    myTank.setMoving(false);
                    Client.INSTANCE.send(new TankStopMsg(getMainTank()));
                } else {
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
                    if (!myTank.isMoving()) {
                        myTank.setMoving(true);
                        Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));
                    }
                    if (oldDir != myTank.getDir()) {
                        Client.INSTANCE.send(new TankDirChangedMsg(getMainTank()));
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
//        g.drawString("敌人的数量:" + enemyTanks.size(), 5, 70);
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

        //java8 stream api
        List<Tank> collect = tanks.values().stream().collect(Collectors.toList());
        for (int i = 0; i <collect.size() ; i++) {
            collect.get(i).paint(g);
        }
//        for (int i = 0; i < enemyTanks.size(); i++) {
//            enemyTanks.get(i).paint(g);
//        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.values().size(); j++) {
                intersectDeal(bullets.get(i), tanks.values().iterator().next());
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }
    }

    private void intersectDeal(Bullet bullet, Tank tank) {
//        if (bullet.getGroup().equals(tank.getGroup())) {
//            return;
//        }
        if (bullet.getPlayerId().equals(tank.getId())) {
            return;
        }
        //优化
        Rectangle bulletRect = bullet.getRectangle();
        Rectangle tankRect = tank.getRectangle();
        if(bullet.isAlive() && tank.isAlive() && bulletRect.intersects(tankRect)) {
            Client.INSTANCE.send(new TankDieMsg(bullet.getId(), tank.getId()));
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

    public Tank getMainTank() {
        return myTank;
    }

    public void addTank(Tank tank) {
        tanks.put(tank.getId(), tank);
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public Bullet findBulletByUUID(UUID bulletId) {
        for (int i = 0; i <bullets.size() ; i++) {
            if (bulletId.equals(bullets.get(i).getId())) {
                return bullets.get(i);
            }
        }
        return null;
    }
}
