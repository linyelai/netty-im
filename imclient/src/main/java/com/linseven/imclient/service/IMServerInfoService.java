package com.linseven.imclient.service;

import com.google.gson.Gson;
import com.linseven.IMServerInfo;
import com.linseven.imclient.IMServerInfoResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class IMServerInfoService {
    String imServerHost = "http://127.0.0.1:3001/getServerInfo";

 public IMServerInfo getIMServerInfo(String token) throws IOException {




     OkHttpClient client = new OkHttpClient();

     Request request = new Request.Builder()
             .addHeader("Authorization","Bearer "+token)
             .url(imServerHost)

             .build();

     try (Response response = client.newCall(request).execute()) {
        String data =    response.body().string();

         IMServerInfoResponse gson = new Gson().fromJson(data, IMServerInfoResponse.class);
         IMServerInfo imServerInfo =  gson.getData();
         return imServerInfo;


     }



 }
}
