package com.mashibing.tank.dp.factorymethod;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:28
 * @description
 */
public class RiceFactory {
    private static final RiceFactory instance = new RiceFactory();
    private RiceFactory() {

    }
    public static RiceFactory getInstance() {
        return instance;
    }
    public EatAble create() {
        return new Rice();
    }
}
