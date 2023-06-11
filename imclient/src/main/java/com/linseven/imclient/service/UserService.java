package com.linseven.imclient.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.linseven.IMServerInfo;
import com.linseven.imclient.AppContext;
import com.linseven.imclient.IMServerInfoResponse;
import com.linseven.imclient.TokenResponse;


import com.linseven.imclient.model.UserInfo;
import okhttp3.*;

import java.io.IOException;

public class UserService {

    String imServerHost = "http://127.0.0.1:3001/login";
    public String login(String username,String password) throws IOException {


        synchronized (UserService.class) {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            RequestBody requestBody = builder.add("username", username).add("password", password).build();
            Request request = new Request.Builder()
                    .url(imServerHost)
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String data = response.body().string();

                TokenResponse tokenResponse = new Gson().fromJson(data, TokenResponse.class);
                String token = tokenResponse.getData();
                return token;


            }
        }


    }




    public IMServerInfo getUserOnlineIMServerInfo(String username,String token) throws IOException {

        synchronized (UserService.class) {
            OkHttpClient client = new OkHttpClient();
            String endpoint = "http://127.0.0.1:3001/getUserOnlineIMServerInfo?username=" + username;
            // String token = AppContext.getContext().getToken();
            Request request = new Request.Builder()
                    .url(endpoint)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String data = response.body().string();

                IMServerInfoResponse tokenResponse = new Gson().fromJson(data, IMServerInfoResponse.class);
                IMServerInfo imServerInfo = tokenResponse.getData();
                return imServerInfo;


            }
        }

    }


    public UserInfo getUserInfo(String token) throws IOException {

        synchronized (UserService.class) {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            String endpoint = "http://127.0.0.1:3001/getUserInfo";
            //String token = AppContext.getContext().getToken();
            Request request = new Request.Builder()
                    .url(endpoint)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String data = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(data);
                UserInfo userInfo = jsonObject.getObject("data", UserInfo.class);
                return userInfo;


            }
        }

    }

}
