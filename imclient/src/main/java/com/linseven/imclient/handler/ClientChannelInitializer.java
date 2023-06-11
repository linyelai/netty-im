package com.linseven.imclient.handler;

import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:50
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private String username;

    public  ClientChannelInitializer(String username){
        this.username = username;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        //p.addLast(new MyHandler());
        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(IMMessageOuterClass.IMMessage.getDefaultInstance()));
        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());
        p.addLast(new NewMsgHandler(this.username));
    }

}