package com.mashibing.tank.dp.singleton;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:41
 * @description
 * 饿汉式单例
 */
public class SingleTonHungry {
    public static final SingleTonHungry singleTon1 = new SingleTonHungry();
    private SingleTonHungry(){}
    public static SingleTonHungry getInstance() {
        return singleTon1;
    }
    public static void main(String[] args) {
        for (int i = 0; i <100 ; i++) {
            new Thread(() -> {
                System.out.println(SingleTonHungry.getInstance());
            }).start();
        }
    }
}
