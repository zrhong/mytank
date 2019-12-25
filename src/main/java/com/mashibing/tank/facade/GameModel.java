package com.mashibing.tank.facade;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.chainofresponsibility.Collider;
import com.mashibing.tank.chainofresponsibility.ColliiderChain;
import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.constant.Group;
import com.mashibing.tank.entity.Tank;
import com.mashibing.tank.mediator.GameObject;
import com.mashibing.tank.util.PropertyMgr;

import java.awt.*;
import java.io.*;
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
    public Tank myTank;
    public List<GameObject> objects = new ArrayList<>();
    public Collider collider = new ColliiderChain();

    private GameModel(){}
    static {
        INSTANCE.init();
    }
    private void init() {
        myTank = new Tank(20, 200, Dir.RIGHT, Group.GOOD);
        int initTankCount = Integer.valueOf((String) PropertyMgr.getProp("initTankCount"));
        for (int i = 0; i < initTankCount; i++) {
            new Tank(TankFrame.GAME_WIDTH - 50, new Random().nextInt(TankFrame.GAME_HEIGHT - 50), Dir.LEFT, Group.BAD);
        }
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.white);
//        g.drawString("子弹的数量:" + bullets.size(), 5, 50);
//        g.drawString("敌人的数量:" + enemyTanks.size(), 5, 70);
//        g.drawString("爆炸的数量:" + explosions.size(), 5, 90);
        g.setColor(color);
        if (myTank != null) {
            myTank.paint(g);
        }
        //迭代器只能在迭代的地方删除，不能其他地方删除，不然会报错  java.util.ConcurrentModificationException
//        for (Bullet b:bullets) {
//            b.paint(g);
//        }
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                Object o1 = objects.get(i);
                Object o2 = objects.get(j);
                collider.collision(o1, o2);
            }
        }
    }

    public Tank getMytank() {
        return myTank;
    }

    public void add(GameObject object) {
        objects.add(object);
    }

    public void remove(GameObject object) {
        objects.remove(object);
    }

    public void  save(){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(
                    new FileOutputStream(
                            new File("E:/ideaWorkspace/mashibing/mytank/src/main/java/com/mashibing/tank/record/tank.data")));
            outputStream.writeObject(myTank);
            outputStream.writeObject(objects);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(){
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(
                    new FileInputStream(
                            new File("E:/ideaWorkspace/mashibing/mytank/src/main/java/com/mashibing/tank/record/tank.data")));
            myTank = (Tank) inputStream.readObject();
            objects = (List) inputStream.readObject();
        } catch (Exception e) {

        }finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

