package com.hackmanchester2016.swearjar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.ui.launch.LaunchFragment;

/**
 * Created by dant on 29/10/2016.
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        showSignIn();
    }

    private void showSignIn() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.launch_fragment_container, new LaunchFragment(), LaunchFragment.TAG)
                .commit();
    }

    public void onSignIn() {
        if(Engine.getInstance().getSetupController().setupComplete()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setData(getIntent().getData());
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, SetupActivity.class);
            intent.setData(getIntent().getData());
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
