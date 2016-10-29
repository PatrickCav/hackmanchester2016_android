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

    public static LaunchFragment newInstance(){
        return new LaunchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setProviders(AuthUI.GOOGLE_PROVIDER)
                        .build(), RC_SIGN_IN);

        super.onCreate(savedInstanceState);
    }

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
