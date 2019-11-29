package com.mashibing.tank.strategy;

import com.mashibing.tank.abstractfactory.BaseTank;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/11/25 19:01
 * @description
 * 开火策略
 */
public interface FireStrategy {
    /**
     * 开火
     * @param tank
     */
    void fire(BaseTank tank);
}
