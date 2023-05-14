package com.linseven.imclient.service;

import com.google.gson.Gson;
import com.linseven.IMServerInfo;
import com.linseven.imclient.IMServerInfoResponse;
import com.linseven.imclient.TokenResponse;
import okhttp3.*;

import java.io.IOException;

public class UserService {

    String imServerHost = "http://127.0.0.1:3001/login";
    public String login(String username,String password) throws IOException {


        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.add("username",username).add("password",password).build();
        Request request = new Request.Builder()
                .url(imServerHost)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String data =    response.body().string();

            TokenResponse tokenResponse = new Gson().fromJson(data, TokenResponse.class);
            String token = tokenResponse.getData();
            return token;


        }


    }


    public IMServerInfo getUserOnlineIMServerInfo(String userId) throws IOException {

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        String  endpoint = "http://127.0.0.1:3001/getUserOnlineIMServerInfo?userId="+userId;
        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String data =    response.body().string();

            IMServerInfoResponse tokenResponse = new Gson().fromJson(data, IMServerInfoResponse.class);
            IMServerInfo imServerInfo = tokenResponse.getData();
            return imServerInfo;


        }

    }
}
