package com.hackmanchester2016.swearjar.engine.comms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;
import com.hackmanchester2016.swearjar.engine.comms.models.ChallengeResponse;
import com.hackmanchester2016.swearjar.engine.comms.models.LocationStatsResponse;
import com.hackmanchester2016.swearjar.engine.comms.models.SendLocationRequest;
import com.hackmanchester2016.swearjar.engine.comms.models.SendTextRequest;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;
import com.hackmanchester2016.swearjar.engine.managers.AuthManager;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("https://n43au9qkce.execute-api.us-west-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
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
        Call<SwearingStatsResponse> getSwearingStats(@Query("challengeId") String id);

        @POST("prod/location")
        Call<Void> sendLocation(@Body SendLocationRequest body);

        @GET("prod/search/location")
        Call<LocationStatsResponse> getLocationStats(@Query("challengeId") String id);

        //               |
        //               |
        // PLS JONNY     V
        @POST("prod/challange")
        Call<Void> createChallenge(@Body Challenge challenge);

        @GET("prod/challange")
        Call<ChallengeResponse> getChallenges();

    }
}
