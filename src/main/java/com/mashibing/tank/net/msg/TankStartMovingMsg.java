package com.mashibing.tank.net.msg;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.UUID;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/25 19:12
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TankStartMovingMsg extends Msg {
    int x, y;
    Dir dir;
    UUID id;


    public TankStartMovingMsg(Tank tank){
        dir = tank.getDir();
        id = tank.getId();
        x = tank.getX();
        y = tank.getY();
    }
    @Override
    public void handle() {
        if (this.id == TankFrame.INSTANCE.getMainTank().getId()) {
            return;
        }
        Tank tank = TankFrame.INSTANCE.findTankByUUID(id);
        if (tank != null) {
            tank.setMoving(true);
            tank.setX(x);
            tank.setY(y);
            tank.setDir(dir);
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream bas = null;
        DataOutputStream dos = null;
        try {
            bas = new ByteArrayOutputStream();
            dos = new DataOutputStream(bas);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bas != null) {
                try {
                    bas.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bas.toByteArray();
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            x = dis.readInt();
            y = dis.readInt();
            dir = Dir.values()[dis.readInt()];
            id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }
}
