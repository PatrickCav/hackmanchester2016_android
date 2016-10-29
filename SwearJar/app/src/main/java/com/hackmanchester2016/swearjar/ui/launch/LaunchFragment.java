package com.hackmanchester2016.swearjar.ui.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hackmanchester2016.swearjar.LaunchActivity;
import com.hackmanchester2016.swearjar.R;

/**
 * Created by dant on 29/10/2016.
 */

public class LaunchFragment extends Fragment {

    public static String TAG = "LAUNCH_FRAGMENT";

    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);

        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(loginClickListener);

        return view;
    }

    private Button.OnClickListener loginClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getActivity() instanceof LaunchActivity) {
                ((LaunchActivity)getActivity()).onSignIn();
            }
        }
    };
}
