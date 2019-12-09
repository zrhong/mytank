package com.mashibing.tank.chainofresponsibility;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/9 17:33
 * @description
 */
public interface Collider {
    /**
     * 物体之间是否碰撞
     * @param o1
     * @param o2
     * @return
     */
    boolean collision(Object o1, Object o2);
}
