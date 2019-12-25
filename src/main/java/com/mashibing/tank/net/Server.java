package com.mashibing.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zhuruihong
 * @version 1.0
 * @date 2019/12/23 15:52
 * @description
 */
public class Server {
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public void serverStart(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture f = bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new MsgEncoder()).addLast(new MsgDecoder()).addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx){
                                    clients.add(ch);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    ServerFrame.INSTANCE.updateClientMsg(msg.toString());
                                    clients.writeAndFlush(msg);
                                }

                                /**
                                 * 客户端异常时应该清除出去
                                 * @param ctx
                                 * @param cause
                                 * @throws Exception
                                 */
                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    clients.remove(ctx.channel());
                                    ctx.close();
                                }
                            });
                        }
                    })
                    .bind(8888)
                    .sync();
            System.out.println("server started");
            ServerFrame.INSTANCE.updateServerMsg("server started");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
