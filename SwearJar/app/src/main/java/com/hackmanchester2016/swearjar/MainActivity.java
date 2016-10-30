package com.hackmanchester2016.swearjar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.service.TextMessageService;
import com.hackmanchester2016.swearjar.ui.home.HomeFragment;
import com.hackmanchester2016.swearjar.ui.launch.LaunchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String PUBLIC_STATIC_ = null;
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.PROCESS_OUTGOING_CALLS") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.RECORD_AUDIO") == PackageManager.PERMISSION_GRANTED) {
            startServices();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS", "android.permission.READ_PHONE_STATE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.RECORD_AUDIO"}, REQUEST_CODE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit();
    }

    public void logout(){
        Engine.getInstance().getAuthManager().signOut();
        stopServices();

        Intent intent = new Intent(this, LaunchActivity.class);
        intent.setData(getIntent().getData());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void startServices() {
        startService(new Intent(this, TextMessageService.class));
    }

    private void stopServices() {
        stopService(new Intent(this, TextMessageService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                for(int result : grantResults) {
                    if(result != PackageManager.PERMISSION_GRANTED) {
                        return; // fuck em
                    }
                }

                startServices();
            }
        }
    }

    public void pushDetailsFragment(Fragment fragment, String tag, boolean allowDuplicated) {
        if(allowDuplicated || getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    public void pushFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        transaction.replace(R.id.container, fragment, null).addToBackStack(null);
        transaction.commit();
    }
}
