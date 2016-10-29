package com.hackmanchester2016.swearjar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hackmanchester2016.swearjar.engine.Engine;

/**
 * Created by dant on 29/10/2016.
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        refreshFragment();
    }

    private void refreshFragment() {
        if(Engine.getInstance().getSetupController().setupComplete()) {
            // TODO start app
        } else {
            // TODO Start setup
        }
    }
}
