package com.hackmanchester2016.swearjar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.service.TextMessageService;
import com.hackmanchester2016.swearjar.ui.home.HomeFragment;
import com.hackmanchester2016.swearjar.ui.launch.LaunchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String PUBLIC_STATIC_ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.PROCESS_OUTGOING_CALLS") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.RECORD_AUDIO") == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(this, TextMessageService.class);
            startService(i);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS", "android.permission.READ_PHONE_STATE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.RECORD_AUDIO"}, 0);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit();
    }

    public void logout(){
        Engine.getInstance().getAuthManager().signOut();
        Intent intent = new Intent(this, LaunchActivity.class);
        intent.setData(getIntent().getData());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
