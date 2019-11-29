package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:28
 * @description
 */
public class CakeFactory {
    private static final CakeFactory instance = new CakeFactory();
    private CakeFactory() {

    }
    public static CakeFactory getInstance() {
        return instance;
    }
    public EatAble create() {
        return new Cake();
    }
}
