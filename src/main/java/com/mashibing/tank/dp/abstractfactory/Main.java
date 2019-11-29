package com.mashibing.tank.dp.abstractfactory;
/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/27 14:54
 * @description
 */
public class Main {
    public static void main(String[] args) {
        ModernFactory.getInstance().createFood().price();
        AncientFactory.getInstance().createFood().price();
        ModernFactory.getInstance().createVehicle().go();
        AncientFactory.getInstance().createVehicle().go();
    }
}
