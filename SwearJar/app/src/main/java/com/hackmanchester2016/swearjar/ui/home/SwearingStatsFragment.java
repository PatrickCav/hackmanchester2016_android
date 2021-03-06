package com.hackmanchester2016.swearjar.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
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
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;
import com.hackmanchester2016.swearjar.engine.managers.FineManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStatsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FineManager.FineListener {

    private static final String TAG = "SwearingStats";

    private static final String CHALLENGE_ID = "challengeId";

    private static final int[] COLOURS = {R.color.pie_0, R.color.pie_1, R.color.pie_2, R.color.pie_3, R.color.pie_4, R.color.pie_5};

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFines;
    private LinearLayout frequencyTable;
    private PieChart pieChart;
    private MoneyBagsView moneyBagsView;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private FloatingActionButton shareButton;

    private Challenge challenge;
    private double totalDonation;

    private int totalFine = 0;

    public static SwearingStatsFragment newInstance(Challenge challenge){
        SwearingStatsFragment fragment = new SwearingStatsFragment();
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

        TwitterAuthConfig authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
        Fabric.with(getContext(), new TwitterCore(authConfig), new TweetComposer());
    }

    @Override
    public void onResume() {
        super.onResume();
        Engine.getInstance().getFineManager().setFineListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Engine.getInstance().getFineManager().removeListener();
    }

    @Override
    public void fineUpdated() {
        Engine.getInstance().getRetrofitClient().getApi().getSwearingStats(DateUtils.formatDate(challenge.fromDate), DateUtils.formatDate(challenge.toDate)).enqueue(new Callback<SwearingStatsResponse>() {
            @Override
            public void onResponse(Call<SwearingStatsResponse> call, Response<SwearingStatsResponse> response) {
                processStats(response.body().stats);
            }

            @Override
            public void onFailure(Call<SwearingStatsResponse> call, Throwable t) {
                totalFines.setText("ERROR YO");
            }
        });
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
        shareButton = (FloatingActionButton) view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(shareListener);

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
        totalFine = 0;
        frequencyTable.removeAllViews();
        moneyBagsView.sweepUpDaCash();
        Engine.getInstance().getRetrofitClient().getApi().getSwearingStats(DateUtils.formatDate(challenge.fromDate), DateUtils.formatDate(challenge.toDate)).enqueue(new Callback<SwearingStatsResponse>() {
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
            Collections.sort(stats);
            populateFrequencyList(stats);
            populatePieChart(stats.subList(0, Math.min(stats.size(), 6)), totalUses);
            setTotalFine(totalUses);
        } else{
            totalFines.setText("You haven't sworn yet‽");
        }
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
            colours.add(getContext().getResources().getColor(COLOURS[i%6]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Swearing Stats");
        dataSet.setDrawValues(false);
        dataSet.setColors(colours);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void populateFrequencyList(List<SwearingStat> stats){
        frequencyTable.removeAllViews();

        for(SwearingStat stat : stats){
            frequencyTable.addView(new FrequencyRow(getContext(), stat.word, Integer.toString(stat.count)));
        }
    }

    private void setTotalFine(int totalTimesSworn){
        int fine = Engine.getInstance().getFineManager().calculateFine(challenge.forfeit, totalTimesSworn);
        totalDonation = fine / 100;

        totalFines.setVisibility(View.VISIBLE);
        animateText(fine);
    }

    private void animateText(final int fine){

        moneyBagsView.makeItRain(fine);
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "formattedText", totalFine, fine);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                totalFine = fine;
            }
        });

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }

    public void setFormattedText(int value){
        totalFines.setText(numberFormat.format(((double) value)/100));
    }

    private View.OnClickListener shareListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DecimalFormat df = new DecimalFormat("#.00");
            String tweet = "Thanks to " + Engine.getInstance().getUserManager().getUser(challenge.challengerId).displayName;
            tweet += ", I've donated £" + df.format(totalDonation) + " to Cancer Research UK!";

            File imagePath = new File(getContext().getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(getContext(), "com.hackmanchester2016.swearjar.fileProvider", newFile);

            URL donationUrl = null;
            try {
                donationUrl = new URL("http://www.cancerresearchuk.org/support-us/donate?gclid=CjwKEAjwtNbABRCsqO7J0_uJxWYSJAAiVo5LonErB4fxQuzGc-0cmK9udbxD7iIOX7WirSHLANEQehoC51Pw_wcB&dclid=CNq26d-ngtACFcyi7QodhyAOVw");
            } catch (MalformedURLException ex){

            }

            TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                    .text(tweet)
                    .url(donationUrl)
                    .image(contentUri);
            builder.show();
        }
    };
}
