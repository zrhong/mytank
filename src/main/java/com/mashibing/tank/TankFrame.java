package com.mashibing.tank;

import com.mashibing.tank.constant.Dir;
import com.mashibing.tank.entity.Tank;
import com.mashibing.tank.facade.GameModel;
import com.mashibing.tank.strategy.FourDirFireStrategy;
import com.mashibing.tank.util.PropertyMgr;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/23 19:59
 * @description
 */

public class TankFrame extends Frame {
    public GameModel gameModel = GameModel.getInstance();
    public static final int GAME_WIDTH, GAME_HEIGHT;

    static {
        GAME_WIDTH = PropertyMgr.getPropInt("gameWidth");
        GAME_HEIGHT = PropertyMgr.getPropInt("gameHeight");
    }

    private static class TankFrameHolder {
        private static final TankFrame INSTANCE = new TankFrame();
    }

    public static TankFrame getInstance() {
        return TankFrameHolder.INSTANCE;
    }

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
        this.addKeyListener(new MyKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        gameModel.paint(g);
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

    class MyKeyListener extends KeyAdapter {
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
            Tank myTank = gameModel.getMytank();
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
                    gameModel.getMytank().fire(FourDirFireStrategy.getInstance());
                    break;
                default:
            }
            setTankDir();
        }
    }
}
