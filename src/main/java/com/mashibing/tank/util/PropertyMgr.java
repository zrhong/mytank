package com.mashibing.tank.util;

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
        return properties == null ? null : properties.get(key);
    }

    public static String getPropString(String key) {
        return properties == null ? null : properties.getProperty(key);
    }

    public static Integer getPropInt(String key) {
        return properties == null ? null : Integer.valueOf(properties.getProperty(key,"800"));
    }

    public static void main(String[] args) {
        System.out.println(PropertyMgr.getProp("initTankCount"));
    }
}
