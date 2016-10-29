package com.hackmanchester2016.swearjar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.ui.setup.SetupFragment;

/**
 * Created by patrickc on 29/10/2016
 */
public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup);

        showSetup();
    }

    private void showSetup() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setup_fragment_container, new SetupFragment(), SetupFragment.TAG)
                .commit();
    }

    public void setupComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setData(getIntent().getData());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
