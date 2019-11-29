package com.mashibing.tank.dp.singleton;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:44
 * @description 双重检查加锁懒汉式
 */
public class SingleTonDCL {
    private volatile static SingleTonDCL instance;
    private SingleTonDCL(){

    }

    public static SingleTonDCL getInstance() {
        if (null == instance) {
            synchronized (SingleTonDCL.class) {
                if (null == instance) {
                    instance = new SingleTonDCL();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i <100 ; i++) {
            new Thread(() -> {
                System.out.println(SingleTonDCL.getInstance());
            }).start();
        }
    }

}
