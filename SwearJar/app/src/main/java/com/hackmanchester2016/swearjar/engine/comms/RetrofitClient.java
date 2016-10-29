package com.hackmanchester2016.swearjar.engine.comms;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by dant on 29/10/2016.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;
    private static NotificationApi api;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        api = retrofit.create(NotificationApi.class);
    }

    public NotificationApi getApi(){
        return api;
    }

    public interface NotificationApi {
        @Headers("Authorization:key=AIzaSyBcuH4edNwPNpnIsXBMmpBmb2PFh9MAn6E")
        @POST("fcm/send")
        Call<Void> sendNotification(@Body Void body);
    }
}
