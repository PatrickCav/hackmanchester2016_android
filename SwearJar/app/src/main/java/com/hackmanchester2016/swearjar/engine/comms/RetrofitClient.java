package com.hackmanchester2016.swearjar.engine.comms;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.ChallengeResponse;
import com.hackmanchester2016.swearjar.engine.comms.models.SendTextRequest;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;
import com.hackmanchester2016.swearjar.engine.managers.AuthManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by the idiot dant on 29/10/2016.
 */

public class RetrofitClient {

    private static SwearyApi api;

    public RetrofitClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", Engine.getInstance().getAuthManager().getUserId())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient.build())
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
        Call<Void> sendText(@Body SendTextRequest body);

        @GET("prod/search/text")
        Call<SwearingStatsResponse> getStats();

        @GET("prod/challenge")
        Call<ChallengeResponse> getChallenges();

    }
}
