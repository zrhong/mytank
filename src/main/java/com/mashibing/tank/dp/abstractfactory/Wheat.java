package com.mashibing.tank.dp.abstractfactory;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 14:53
 * @description
 */
public class Wheat extends Food {
    @Override
    public void price() {
        System.out.println("wheat $1");
    }
}
