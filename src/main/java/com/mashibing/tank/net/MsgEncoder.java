package com.mashibing.tank.net;

import com.mashibing.tank.net.msg.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/25 14:50
 * @description 消息编码器
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
        //消息类型
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        //消息的长度
        buf.writeInt(bytes.length);
        //最后面才是消息体
        buf.writeBytes(bytes);
    }
}
