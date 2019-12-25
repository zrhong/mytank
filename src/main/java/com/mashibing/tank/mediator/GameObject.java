package com.mashibing.tank.mediator;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/9 13:17
 * @description
 */
@Data
public abstract class GameObject implements Serializable {
    public int x, y;

    public abstract void paint(Graphics g);

    public GameObject(){

    }
}
