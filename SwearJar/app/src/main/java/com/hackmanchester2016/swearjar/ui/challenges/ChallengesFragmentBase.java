package com.hackmanchester2016.swearjar.ui.challenges;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.MainActivity;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;

import java.util.List;

/**
 * Created by dant on 30/10/2016.
 */

public abstract class ChallengesFragmentBase extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ChallengesAdapterBase.ChallengesCallback{

    protected SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton addChallengeButton;

    private ChallengesAdapterBase adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        adapter = getAdapter(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addChallengeButton = (FloatingActionButton) view.findViewById(R.id.add_challenge);
        addChallengeButton.setOnClickListener(addChallengeListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onRefresh();

        super.onViewCreated(view, savedInstanceState);
    }

    protected void processChallenges(List<Challenge> challenges) {
        adapter.setChallenges(challenges);
        adapter.notifyDataSetChanged();
    }

    protected abstract ChallengesAdapterBase getAdapter(ChallengesAdapterBase.ChallengesCallback callback);

    protected abstract void requestChallenges();

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        requestChallenges();
    }

    private FloatingActionButton.OnClickListener addChallengeListener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).pushDetailsFragment(new ChallengesFragmentAdd(),
                        ChallengesFragmentAdd.TAG, false);
            }
        }
    };

    @Override
    public void viewChallengeDetails(Challenge challenge) {

    }
}
