package com.mashibing.tank.net;

import com.mashibing.tank.TankFrame;
import com.mashibing.tank.net.msg.Msg;
import com.mashibing.tank.net.msg.TankJoinMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/23 15:47
 * @description
 */
public class Client {
    private Channel channel = null;
    public static final Client INSTANCE = new Client();
    private Client(){

    }
    public void connect(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture f = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new MsgEncoder()).addLast(new MsgDecoder()).addLast(new SimpleChannelInboundHandler<Msg>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    channel = ctx.channel();
                                    ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Msg msg) {
                                    msg.handle();
                                }
                            });
                        }


                    })
                    .connect("127.0.0.1", 8888)
                    .sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }
}
