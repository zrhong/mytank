package com.mashibing.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/24 20:37
 * @description
 */
public class PropertyMgr {
    static volatile Properties properties = new Properties();
    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object getProp(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertyMgr.getProp("initTankCount"));
    }
}
