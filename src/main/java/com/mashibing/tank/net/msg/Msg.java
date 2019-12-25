package com.mashibing.tank.net.msg;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/25 15:26
 * @description
 */
public abstract class Msg {
    //处理坦克的发送消息
    public abstract void handle();
    //方便ByteBuf的使用
    public abstract byte[] toBytes();
    //decode
    public abstract void parse(byte[] bytes);
    //获取消息类型
    public abstract MsgType getMsgType();
}
