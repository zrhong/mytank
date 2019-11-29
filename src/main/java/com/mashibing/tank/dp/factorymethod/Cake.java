package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:26
 * @description
 */
public class Cake implements EatAble {
    @Override
    public void sell() {
        System.out.println("蛋糕卖了5块钱");
    }

    @Override
    public void eat() {
        System.out.println("蛋糕被吃了");
    }
}
