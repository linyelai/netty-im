package com.linseven.imclient.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.linseven.IMServerInfo;
import com.linseven.imclient.AppContext;
import com.linseven.imclient.IMServerInfoResponse;
import com.linseven.imclient.exception.SendMsgFailedException;
import com.linseven.imclient.request.UnReadMsgRequest;
import com.linseven.protobuf.IMMessageOuterClass;
import okhttp3.*;

import java.io.IOException;

public class MessageService {



    public void sendMsg(IMMessageOuterClass.IMMessage message,String endPoint) throws IOException {


        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();
        UnReadMsgRequest unReadMsgRequest = new UnReadMsgRequest();
        unReadMsgRequest.setContent(message.getContent());
        unReadMsgRequest.setSourceId(message.getSourceId());
        unReadMsgRequest.setDestId(message.getDestId());
        unReadMsgRequest.setType(message.getType());
        unReadMsgRequest.setSubType(message.getSubType());
        unReadMsgRequest.setSendTime(message.getSendTime());
        unReadMsgRequest.setMsgId(message.getMsgId());
        String content = gson.toJson(unReadMsgRequest);
        RequestBody body = RequestBody.create(content,MediaType.parse("application/json; charset=utf-8"));
        String token = AppContext.getContext().getToken();
        Request request = new Request.Builder()
                .url(endPoint)
                .post(body)
                .addHeader("Authorization","Bearer "+token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String data =    response.body().string();
            boolean flag = new Gson().fromJson(data, Boolean.class);

            if(!flag){
                throw new SendMsgFailedException("send msg failed");
            }


        }catch (Exception e){
            throw new SendMsgFailedException("send msg failed");
        }



    }
}
