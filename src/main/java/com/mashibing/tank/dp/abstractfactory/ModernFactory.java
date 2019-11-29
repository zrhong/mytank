package com.mashibing.tank.dp.abstractfactory;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 14:45
 * @description
 */
public class ModernFactory extends AbstractClass {
    private static final ModernFactory instance = new ModernFactory();

    public static ModernFactory getInstance() {
        return instance;
    }
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }

    @Override
    public Food createFood() {
        return new Rice();
    }
}
