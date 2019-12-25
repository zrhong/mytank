package com.mashibing.tank.test.server.netty.netty02answer;

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
 * @date 2019/12/23 11:08
 * @description  netty所有方法都是异步
 */
public class Client {
    public static void main(String[] args) throws Exception{
        //线程池组 负责抽出线程去处理连接、读写等，默认大小是cpu核数的两倍
        EventLoopGroup group = new NioEventLoopGroup();
        //辅助类启动器
        Bootstrap bootstrap = new Bootstrap();
        //使用什么线程池
        ChannelFuture f = bootstrap.group(group)
                //使用网络编程模型，bio、nio
                .channel(NioSocketChannel.class)
                //连接之前要干点啥
                .handler(new MyChannelInitializer())
                .connect("127.0.0.1", 8888).addListener((ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        System.out.println("connnet success");
                    } else {
                        System.out.println("connnet fail");
                    }
                    //这个是connect的同步
                }).sync();
        //这个是整个程序的同步
        f.channel().closeFuture().sync();

    }

}

class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new MyClinetChildHandler());
    }
}


class MyClinetChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        System.out.println(new String(bytes));

    }
}