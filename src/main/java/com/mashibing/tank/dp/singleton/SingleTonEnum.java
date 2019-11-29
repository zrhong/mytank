package com.mashibing.tank.dp.singleton;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 20:03
 * @description
 */
public enum SingleTonEnum {
    instance;
public void m(){
    System.out.println("m");
}
    public static void main(String[] args) {
        for (int i = 0; i <100 ; i++) {
            new Thread(() -> System.out.println(SingleTonEnum.instance)).start();
        }
    }
}
