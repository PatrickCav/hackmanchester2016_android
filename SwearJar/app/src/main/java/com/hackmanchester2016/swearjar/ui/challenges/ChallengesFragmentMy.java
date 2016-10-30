package com.hackmanchester2016.swearjar.ui.challenges;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.ChallengeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesFragmentMy extends ChallengesFragmentBase {

    public static final String TITLE = "My Challenges";

    @Override
    protected void requestChallenges() {
        Engine.getInstance().getRetrofitClient().getApi().getMyChallenges().enqueue(new Callback<ChallengeResponse>() {
            @Override
            public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.body() != null) {
                    processChallenges(response.body().results);
                }
            }

            @Override
            public void onFailure(Call<ChallengeResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected ChallengesAdapterBase getAdapter(ChallengesAdapterBase.ChallengesCallback callback) {
        return new ChallengesAdapterMy(callback);
    }
}
