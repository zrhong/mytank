package com.mashibing.tank.net.msg;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Dir;
import com.mashibing.tank.TankFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.UUID;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/26 13:22
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletNewMsg extends Msg {
    int x, y;
    Dir dir;
    UUID id;
    UUID playerId;

    public BulletNewMsg(Bullet bullet) {
        x = bullet.getX();
        y = bullet.getY();
        dir = bullet.getDir();
        id = bullet.getId();
        playerId = bullet.getPlayerId();
    }


    @Override
    public void handle() {
        if (this.playerId.equals(TankFrame.INSTANCE.getMainTank().getId())) {
            return;
        }

        Bullet bullet = new Bullet(playerId, x, y, dir, TankFrame.INSTANCE);
        TankFrame.INSTANCE.addBullet(bullet);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            //先写主战坦克id
            dos.writeLong(this.playerId.getMostSignificantBits());
            dos.writeLong(this.playerId.getLeastSignificantBits());
            //写子弹id
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
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
            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
//            this.group = Group.values()[dis.readInt()];
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
        return MsgType.BulletNew;
    }
}
