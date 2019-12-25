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
        //netty辅助启动类
        Bootstrap bootstrap = new Bootstrap();
        //工作的线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture f = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    //通道初始化
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //管道责任链
                            ch.pipeline().addLast(new MsgEncoder()).addLast(new MsgDecoder()).addLast(new SimpleChannelInboundHandler<Msg>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    //获取通道
                                    channel = ctx.channel();
                                    //发送坦克加入消息
                                    ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Msg msg) {
                                    //处理别人家坦克加入的消息
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
