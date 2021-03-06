package com.hackmanchester2016.swearjar.ui.home;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hackmanchester2016.swearjar.MainActivity;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.DateUtils;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;
import com.hackmanchester2016.swearjar.engine.comms.models.LocationStat;
import com.hackmanchester2016.swearjar.engine.comms.models.LocationStatsResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickc on 30/10/2016
 */
public class LocationStatsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String CHALLENGE_ID = "challengeId";

    private static final int[] COLOURS = {R.color.pie_0, R.color.pie_1, R.color.pie_2, R.color.pie_3, R.color.pie_4, R.color.pie_5};

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFines;
    private LinearLayout frequencyTable;
    private PieChart pieChart;
    private MoneyBagsView moneyBagsView;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    private Challenge challenge;

    public static LocationStatsFragment newInstance(Challenge challenge){
        LocationStatsFragment fragment = new LocationStatsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHALLENGE_ID, challenge);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        challenge = (Challenge) args.getSerializable(CHALLENGE_ID);
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
        frequencyTable = (LinearLayout) view.findViewById(R.id.frequency_table_layout);
        moneyBagsView = (MoneyBagsView) view.findViewById(R.id.money_bags_view);

        view.findViewById(R.id.donate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).donateNow();
            }
        });

        pieChart.setDescription(null);
        pieChart.setNoDataText(null);
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
        pieChart.setData(null);
        pieChart.invalidate();
        frequencyTable.removeAllViews();
        moneyBagsView.sweepUpDaCash();
        Engine.getInstance().getRetrofitClient().getApi().getLocationStats(DateUtils.formatDate(challenge.fromDate), DateUtils.formatDate(challenge.toDate)).enqueue(new Callback<LocationStatsResponse>() {
            @Override
            public void onResponse(Call<LocationStatsResponse> call, Response<LocationStatsResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                processStats(response.body().results);
            }

            @Override
            public void onFailure(Call<LocationStatsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                totalFines.setText("ERROR YO");
            }
        });
    }

    private void processStats(List<LocationStat> stats){
        int totalVisits = 0;
        int fineVisits = 0;

        if(stats != null) {
            for (LocationStat stat : stats) {
                totalVisits += stat.count;
                if(stat.place.equals(challenge.challengeType)) {
                    fineVisits += stat.count;
                }
            }
            Collections.sort(stats);
            populateFrequencyList(stats);
            populatePieChart(stats.subList(0, Math.min(stats.size(), 6)), totalVisits);
            setTotalFine(fineVisits);
        } else{
            totalFines.setText("You haven't been anywhere yet‽");
        }
    }

    private void populatePieChart(List<LocationStat> stats, int total) {

        if(total == 0){
            pieChart.setVisibility(View.GONE);
            return;
        } else{
            pieChart.setVisibility(View.VISIBLE);
        }

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();

        LocationStat stat;
        for(int i = 0; i < stats.size(); i++){
            stat = stats.get(i);
            entries.add(new PieEntry(100 * (stat.count /((float) total)), stat.place));
            colours.add(getContext().getResources().getColor(COLOURS[i%6]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Swearing Stats");
        dataSet.setDrawValues(false);
        dataSet.setColors(colours);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void populateFrequencyList(List<LocationStat> stats){
        frequencyTable.removeAllViews();

        for(LocationStat stat : stats){
            frequencyTable.addView(new FrequencyRow(getContext(), stat.place, Integer.toString(stat.count)));
        }
    }

    private void setTotalFine(int totalTimesSworn){
        int fine = Engine.getInstance().getFineManager().calculateFine(challenge.forfeit, totalTimesSworn);

        totalFines.setVisibility(View.VISIBLE);
        animateText(fine);

        Engine.getInstance().getFineManager().setFineValue(fine);
    }

    private void animateText(int fine){

        moneyBagsView.makeItRain(fine);
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "formattedText", 0, fine);

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }

    public void setFormattedText(int value){
        totalFines.setText(numberFormat.format(((double) value)/100));
    }


}
