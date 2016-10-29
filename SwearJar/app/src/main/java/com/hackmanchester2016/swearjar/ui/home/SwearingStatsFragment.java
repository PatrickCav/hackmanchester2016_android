package com.hackmanchester2016.swearjar.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStatsFragment extends Fragment {

    private static final double FINE_CONVERSION = 137.035999;

    private ProgressBar spinner;
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

        spinner = (ProgressBar) view.findViewById(R.id.swearing_stats_spinner);
        totalFines = (TextView) view.findViewById(R.id.total_fines_text);

        totalFines.setVisibility(View.INVISIBLE);

        requestFines();
    }

    private void requestFines() {
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.GONE);

                List<SwearingStat> list = new ArrayList<>();
                list.add(SwearingStat.testStat());

                processStats(list);
            }
        }, 2000);
//        Engine.getInstance().getRetrofitClient().getApi().getStats().enqueue(new Callback<SwearingStatsResponse>() {
//            @Override
//            public void onResponse(Call<SwearingStatsResponse> call, Response<SwearingStatsResponse> response) {
//                spinner.setVisibility(View.GONE);
//                processStats(response.body().stats);
//            }
//
//            @Override
//            public void onFailure(Call<SwearingStatsResponse> call, Throwable t) {
//
//            }
//        });
    }

    private void processStats(List<SwearingStat> stats){
        int totalUses = 0;

        for(SwearingStat stat : stats){
            totalUses += stat.numberOfUses;
        }

        setTotalFine(totalUses);
    }

    private void setTotalFine(int totalTimesSworn){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        totalFines.setVisibility(View.VISIBLE);
        totalFines.setText(formatter.format(FINE_CONVERSION * totalTimesSworn));
    }
}
