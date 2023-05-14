package com.linseven.imserver.handler;

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
    private ProtobufVarint32FrameDecoder protobufVarint32FrameDecoder;
    @Autowired
    private ProtobufDecoder protobufDecoder;
    @Autowired
    private ConnectServerHandler connectServerHandler;
    @Autowired
    private ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender;
    @Autowired
    private ProtobufEncoder protobufEncoder;
    @Autowired
    private MsgEncoderHandler msgEncoderHandler;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(protobufVarint32FrameDecoder);
        p.addLast(protobufDecoder);
        p.addLast(connectServerHandler);
        p.addLast(protobufVarint32LengthFieldPrepender);
        p.addLast(protobufEncoder);
        p.addLast(msgEncoderHandler);

    }

}