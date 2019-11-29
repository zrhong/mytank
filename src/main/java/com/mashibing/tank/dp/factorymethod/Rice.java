package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:25
 * @description
 */
public class Rice implements EatAble {
    @Override
    public void sell() {
        System.out.println("米卖了1块钱");
    }

    @Override
    public void eat() {
        System.out.println("吃饭了");
    }
}
