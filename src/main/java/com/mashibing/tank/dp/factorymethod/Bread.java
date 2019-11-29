package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:24
 * @description
 */
public class Bread implements EatAble {
    @Override
    public void sell() {
        System.out.println("面包卖了5毛钱");
    }

    @Override
    public void eat() {
        System.out.println("面包被吃了");
    }
}
