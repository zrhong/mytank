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
    UUID id;

    public TankDieMsg(UUID bulletId, UUID id) {
        this.bulletId = bulletId;
        this.id = id;
    }
    @Override
    public void handle() {
        Bullet bullet = TankFrame.INSTANCE.findBulletByUUID(bulletId);
        if (bullet != null) {
            bullet.die();
        }
        //自己的坦克被挂了
        if (id.equals(TankFrame.INSTANCE.getMainTank().getId())) {
            TankFrame.INSTANCE.getMainTank().die();
        } else {
            Tank tank = TankFrame.INSTANCE.findTankByUUID(id);
            if (tank != null) {
                System.out.println(TankFrame.INSTANCE.tanks.remove(id));
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
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
            this.id = new UUID(dis.readLong(), dis.readLong());
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
