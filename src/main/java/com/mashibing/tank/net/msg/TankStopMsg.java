package com.mashibing.tank.net.msg;

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
 * @date 2019/12/25 19:47
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TankStopMsg extends Msg {
    int x, y;
    UUID id;

    public TankStopMsg(Tank tank){
        x = tank.getX();
        y = tank.getY();
        id = tank.getId();
    }
    @Override
    public void handle() {
        if (id == TankFrame.INSTANCE.getMainTank().getId()) {
            return;
        }
        Tank tank = TankFrame.INSTANCE.findTankByUUID(id);
        if (tank != null) {
            tank.setMoving(false);
            tank.setX(x);
            tank.setY(y);
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
        } catch (IOException e) {

        }finally {
            if (bos != null) {
                try {
                    bos.close();
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
        return bos.toByteArray();
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            x = dis.readInt();
            y = dis.readInt();
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
        return MsgType.TankStop;
    }
}
