package com.mashibing.tank.chainofresponsibility;

import com.mashibing.tank.util.PropertyMgr;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/9 17:48
 * @description
 */
public class ColliiderChain implements Collider{
    private List<Collider> colliders = new ArrayList<>();

    public ColliiderChain(){
        String[] strings = PropertyMgr.getPropString("colliders").split(",");
        for (int i = 0; i <strings.length ; i++) {
            try {
                colliders.add((Collider)Class.forName(strings[i]).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean collision(Object o1, Object o2) {
        for (int i = 0; i <colliders.size() ; i++) {
            if (colliders.get(i).collision(o1, o2)) {
                return true;
            }
        }
        return false;
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }
}
