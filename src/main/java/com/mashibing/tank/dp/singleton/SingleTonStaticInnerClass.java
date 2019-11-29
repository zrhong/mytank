package com.mashibing.tank.dp.singleton;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:58
 * @description
 */
public class SingleTonStaticInnerClass {
    private SingleTonStaticInnerClass(){}
    private static class SingleTonStaticInnerClassHolder{
        private static final SingleTonStaticInnerClass instance = new SingleTonStaticInnerClass();
    }
    public static SingleTonStaticInnerClass getInstance(){
        return SingleTonStaticInnerClassHolder.instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(SingleTonStaticInnerClass.getInstance())).start();
        }
    }
}
