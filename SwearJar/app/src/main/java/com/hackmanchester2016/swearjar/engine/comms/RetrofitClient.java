package com.hackmanchester2016.swearjar.engine.comms;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SendTextRequest;
import com.hackmanchester2016.swearjar.engine.managers.AuthManager;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by the idiot dant on 29/10/2016.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;
    private static SwearyApi api;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://n43au9qkce.execute-api.us-west-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        api = retrofit.create(SwearyApi.class);
    }

    public SwearyApi getApi(){
        return api;
    }

    public interface SwearyApi {
        @POST("prod/detect/text")
        Call<Void> sendText(@Header("Authorization") String id, @Body SendTextRequest body);
    }
}
