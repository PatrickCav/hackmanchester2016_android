package com.hackmanchester2016.swearjar.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStatsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SwearingStats";

    private static final double FINE_CONVERSION = 137.035999;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFines;

    public static SwearingStatsFragment newInstance(){
        return new SwearingStatsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_swearing_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        totalFines = (TextView) view.findViewById(R.id.total_fines_text);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        requestFines();
    }

    private void requestFines() {
        totalFines.setVisibility(View.INVISIBLE);
        Engine.getInstance().getRetrofitClient().getApi().getStats().enqueue(new Callback<SwearingStatsResponse>() {
            @Override
            public void onResponse(Call<SwearingStatsResponse> call, Response<SwearingStatsResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                processStats(response.body().stats);
            }

            @Override
            public void onFailure(Call<SwearingStatsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                totalFines.setText("ERROR YO");
            }
        });
    }

    private void processStats(List<SwearingStat> stats){
        int totalUses = 0;

        if(stats != null) {
            for (SwearingStat stat : stats) {
                totalUses += stat.count;
            }
        }

        setTotalFine(totalUses);
    }

    private void setTotalFine(int totalTimesSworn){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        totalFines.setVisibility(View.VISIBLE);
        totalFines.setText(formatter.format((FINE_CONVERSION/100) * totalTimesSworn));
    }
}
