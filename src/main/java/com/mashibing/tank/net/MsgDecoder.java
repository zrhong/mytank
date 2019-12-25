package com.mashibing.tank.net;

import com.mashibing.tank.net.msg.Msg;
import com.mashibing.tank.net.msg.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/25 14:53
 * @description 消息解码器
 *
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //消息头的长度
        if(in.readableBytes() < 8) {
            return;
        }
        //记录这次读到的位置
        in.markReaderIndex();

        MsgType msgType = MsgType.values()[in.readInt()];
        //获取消息的长度
        int length = in.readInt();

        if(in.readableBytes()< length) {
            //消息还不够，重置读的位置
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Msg msg = null;

        //reflection
        msg = (Msg)Class.forName("com.mashibing.tank.net.msg." + msgType.toString() + "Msg").getDeclaredConstructor().newInstance();
		/*switch(msgType) {
		case TankJoin:
			msg = new TankJoinMsg();
			break;
		case TankStartMoving:
			msg = new TankStartMovingMsg();
			break;
		case TankStop:
			msg = new TankStopMsg();
			break;
		case Tank
		default:
			break;
		}*/

        msg.parse(bytes);
        out.add(msg);
    }
}
