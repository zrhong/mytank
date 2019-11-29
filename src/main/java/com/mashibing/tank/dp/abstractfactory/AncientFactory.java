package com.mashibing.tank.dp.abstractfactory;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 14:51
 * @description
 */
public class AncientFactory extends AbstractClass {
    private static final AncientFactory instance = new AncientFactory();

    public static AncientFactory getInstance() {
        return instance;
    }
    @Override
    public Vehicle createVehicle() {
        return new Carriage();
    }

    @Override
    public Food createFood() {
        return new Wheat();
    }
}
