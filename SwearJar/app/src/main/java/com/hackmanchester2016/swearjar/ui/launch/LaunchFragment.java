package com.hackmanchester2016.swearjar.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.hackmanchester2016.swearjar.LaunchActivity;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dant on 29/10/2016.
 */

public class LaunchFragment extends Fragment {

    private static final int RC_SIGN_IN = 42;

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

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(AuthUI.GOOGLE_PROVIDER)
                            .build(), RC_SIGN_IN);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Log.d(TAG, "user Id " + Engine.getInstance().getAuthManager().getUserId());

            if(resultCode == RESULT_OK){
                if(getActivity() instanceof LaunchActivity) {
                    ((LaunchActivity)getActivity()).onSignIn();
                }
                Log.d(TAG, "Sign in successful");
            } else{
                Log.d(TAG, "Sign in failed");
            }
        }
    }
}
