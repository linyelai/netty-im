package com.linseven.imserver.handler;

import com.linseven.imserver.utils.RedisUtil;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:47
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private   RedisUtil redisUtil;

    private   ConnectServerHandler connectServerHandler ;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

         if(connectServerHandler ==null){
             connectServerHandler =  new ConnectServerHandler(redisUtil);
         }
        ChannelPipeline p = ch.pipeline();
        p.addLast( new  ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(IMMessageOuterClass.IMMessage.getDefaultInstance()));
        p.addLast(connectServerHandler);
        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast( new ProtobufEncoder());
        //p.addLast(msgEncoderHandler);


    }

}