package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:27
 * @description
 */
public class Main {
    public static void main(String[] args) {
        BreadFactory.getInstance().create().eat();
        CakeFactory.getInstance().create().sell();
        RiceFactory.getInstance().create().sell();

    }
}
