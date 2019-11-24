package com.mashibing.tank;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/24 14:12
 * @description
 */
public class ResourceMgr {
    //好坦克的四个方向图片
    static BufferedImage gtl, gtr, gtu, gtd;
    //好坦克的四个方向图片
    static BufferedImage btl, btr, btu, btd;
    //子弹的四个方向图片
    static BufferedImage bl, br, bu, bd;

    static BufferedImage[] explosions = new BufferedImage[16];

    static {
        try {
            gtu = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png")));
            gtl = ImageUtil.rotateImage(gtu, -90);
            gtr = ImageUtil.rotateImage(gtu, 90);
            gtd = ImageUtil.rotateImage(gtu, 180);

            btu = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png")));
            btl = ImageUtil.rotateImage(btu, -90);
            btr = ImageUtil.rotateImage(btu, 90);
            btd = ImageUtil.rotateImage(btu, 180);


            bu = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png")));
            bl = ImageUtil.rotateImage(bu, -90);
            br = ImageUtil.rotateImage(bu, 90);
            bd = ImageUtil.rotateImage(bu, 180);

            for (int i = 0; i < explosions.length; i++) {
                explosions[i] = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/" + "e" + (i + 1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
