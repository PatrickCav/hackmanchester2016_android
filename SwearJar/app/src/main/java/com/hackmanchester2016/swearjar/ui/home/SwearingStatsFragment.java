package com.hackmanchester2016.swearjar.ui.home;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStatsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SwearingStats";

    private static final int[] COLOURS = {R.color.pie_0, R.color.pie_1, R.color.pie_2, R.color.pie_3, R.color.pie_4, R.color.pie_5};

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFines;
    private PieChart pieChart;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

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
        pieChart = (PieChart) view.findViewById(R.id.pie_chart);

        pieChart.setDescription(null);
        pieChart.getLegend().setEnabled(false);

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
            populatePieChart(orderData(stats), totalUses);
            setTotalFine(totalUses);
        } else{
            totalFines.setText("You haven't sworn yet‽");
        }
    }

    private List<SwearingStat> orderData(List<SwearingStat> list){
        Collections.sort(list);
        // Max of 6 (six) swears
        return list.subList(0, Math.min(list.size(), 6));
    }

    private void populatePieChart(List<SwearingStat> stats, int total) {

        if(total == 0){
            pieChart.setVisibility(View.GONE);
            return;
        } else{
            pieChart.setVisibility(View.VISIBLE);
        }

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();

        SwearingStat stat;
        for(int i = 0; i < stats.size(); i++){
            stat = stats.get(i);
            entries.add(new PieEntry(100 * (stat.count /((float) total)), stat.word));
            colours.add(COLOURS[i%4]);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Swearing Stats");
        dataSet.setDrawValues(false);
        dataSet.setColors(colours);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void setTotalFine(int totalTimesSworn){
        int fine = Engine.getInstance().getFineManager().calculateFine(totalTimesSworn);

        totalFines.setVisibility(View.VISIBLE);
        totalFines.setText(Engine.getInstance().getFineManager().getFormattedFine(fine));

        Engine.getInstance().getFineManager().setFineValue(fine);
    }

    private void animateText(int fine){
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "formattedText", 0, fine);

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }

    public void setFormattedText(int value){
        totalFines.setText(numberFormat.format(((double) value)/100));
    }

}
