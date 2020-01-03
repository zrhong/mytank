package com.mashibing.tank.net.msg;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.UUID;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/26 14:32
 * @description
 */
@Data
@NoArgsConstructor
public class TankDieMsg extends Msg {
    UUID bulletId;
    UUID tankId;

    public TankDieMsg(UUID bulletId, UUID id) {
        this.bulletId = bulletId;
        this.tankId = id;
    }
    @Override
    public void handle() {
        Bullet bullet = TankFrame.INSTANCE.findBulletByUUID(bulletId);
        if (bullet != null) {
            bullet.die();
        }
        //自己的坦克被挂了
        if (tankId.equals(TankFrame.INSTANCE.getMainTank().getId())) {
            TankFrame.INSTANCE.getMainTank().die();
        } else {
            Tank tank = TankFrame.INSTANCE.findTankByUUID(tankId);
            if (tank != null) {
                TankFrame.INSTANCE.tanks.remove(tankId);
                tank.die();
            }
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
            dos.writeLong(tankId.getMostSignificantBits());
            dos.writeLong(tankId.getLeastSignificantBits());
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
            this.tankId = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }
}
