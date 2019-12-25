import com.mashibing.tank.Dir;
import com.mashibing.tank.net.MsgDecoder;
import com.mashibing.tank.net.MsgEncoder;
import com.mashibing.tank.net.msg.MsgType;
import com.mashibing.tank.net.msg.TankJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/25 14:58
 * @description
 */
public class TankJoinMsgTest {
    /**
     * 测试数据经过encoder后是否正确
     */
    @Test
    public void testEncoder() {
        //通道
        EmbeddedChannel ch = new EmbeddedChannel();
        //通道加入责任链的encoder
        ch.pipeline().addLast(new MsgEncoder());
        UUID id = UUID.randomUUID();
        //新建测试数据
        TankJoinMsg tankJoinMsg = new TankJoinMsg(10, 20, Dir.RIGHT, false, id);
        //写出数据
        ch.writeOutbound(tankJoinMsg);
        ByteBuf byteBuf = ch.readOutbound();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];//4字节
        int length = byteBuf.readInt();//4字节
        int x = byteBuf.readInt();//4字节
        int y = byteBuf.readInt();//4字节
        Dir dir = Dir.values()[byteBuf.readInt()];//4字节
        boolean moving = byteBuf.readBoolean(); //1字节
        UUID uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());//16字节
        Assert.assertEquals(MsgType.TankJoin, msgType);
        Assert.assertEquals(false, moving);
        Assert.assertEquals(29, length);
        Assert.assertEquals(x, 10);
        Assert.assertEquals(y, 20);
        Assert.assertEquals(uuid, id);
        Assert.assertEquals(y, 20);
        Assert.assertEquals(dir, Dir.RIGHT);
    }

    @Test
    public void testDecoder(){
        //通道
        EmbeddedChannel ch = new EmbeddedChannel();
        //通道加入责任链的encoder
        ch.pipeline().addLast(new MsgDecoder());
        ByteBuf byteBuf = Unpooled.buffer();
        //新建测试数据
        UUID id = UUID.randomUUID();
        TankJoinMsg tankJoinMsg = new TankJoinMsg(10, 20, Dir.RIGHT,false,id);
        byteBuf.writeInt(MsgType.TankJoin.ordinal());
        byteBuf.writeInt(tankJoinMsg.toBytes().length);
        byteBuf.writeBytes(tankJoinMsg.toBytes());
        ch.writeInbound(byteBuf.duplicate());
        TankJoinMsg tankJoinMsg1 = ch.readInbound();
        Assert.assertEquals(tankJoinMsg.getDir(), tankJoinMsg1.getDir());
        Assert.assertEquals(tankJoinMsg.getX(), tankJoinMsg1.getX());
        Assert.assertEquals(tankJoinMsg.getY(), tankJoinMsg1.getY());
        Assert.assertEquals(tankJoinMsg.getId(), tankJoinMsg1.getId());
        Assert.assertEquals(tankJoinMsg.getMsgType(), tankJoinMsg1.getMsgType());
        Assert.assertEquals(tankJoinMsg.isMoving(), tankJoinMsg1.isMoving());
    }
}
