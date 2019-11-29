package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:28
 * @description
 */
public class BreadFactory {
    private static final BreadFactory instance = new BreadFactory();
    private BreadFactory() {

    }
    public static BreadFactory getInstance() {
        return instance;
    }
    public EatAble create() {
        return new Bread();
    }
}
