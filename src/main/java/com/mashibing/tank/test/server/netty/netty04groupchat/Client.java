package com.mashibing.tank.test.server.netty.netty04groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
    public void connect(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture f = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    channel = ctx.channel();
//                                    ByteBuf byteBuf = Unpooled.copiedBuffer("hello".getBytes());
//                                    ctx.writeAndFlush(byteBuf);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    byte[] bytes = new byte[byteBuf.readableBytes()];
                                    byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                                    String msgAccepted = new String(bytes);
                                    ClientFrame.getInstance().updateMsg(msgAccepted);
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
    public static void main(String[] args) {
        new Client().connect();

    }

    public void sendMsg(String text) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(text.getBytes());
        channel.writeAndFlush(byteBuf);
    }
}
