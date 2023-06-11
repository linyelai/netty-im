package com.linseven.imserver.config;


import com.linseven.imserver.handler.ConnectServerHandler;
import com.linseven.imserver.handler.MsgEncoderHandler;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HandlerConfig {


    @Bean
    @Scope("prototype")
    public ProtobufVarint32FrameDecoder protobufVarint32FrameDecoder(){
        return new  ProtobufVarint32FrameDecoder();
    }

    @Bean
    @Scope("prototype")
    public ProtobufDecoder protobufDecoder(){
        return new ProtobufDecoder(IMMessageOuterClass.IMMessage.getDefaultInstance());

    }

    @Bean
    @Scope("prototype")
    public ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender(){
        return new ProtobufVarint32LengthFieldPrepender();

    }
    @Bean
    @Scope("prototype")
    public ProtobufEncoder protobufEncoder(){

        return new ProtobufEncoder();
    }

}
