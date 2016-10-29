package com.hackmanchester2016.swearjar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hackmanchester2016.swearjar.service.TextMessageService;
import com.hackmanchester2016.swearjar.service.util.MicManager;

public class TestMicActivity extends AppCompatActivity {

    private MicManager mMicManager;
    private TextView resultOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mic);

        mMicManager = new MicManager(this, new MicManager.VoiceDetectionResultListener() {
            @Override
            public void onResult(String result) {
                resultOutput.setText(result);
            }

            @Override
            public void onPartialResult(String result) {
                resultOutput.setText(result);
            }
        });

        Button startRecording = (Button) findViewById(R.id.startRecording);
        Button stopRecording = (Button) findViewById(R.id.stopRecording);
        resultOutput = (TextView) findViewById(R.id.resultsOutput);

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultOutput.setText("");
                mMicManager.startListening();
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMicManager.stopListening();
            }
        });

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TestMicActivity.this, new String[]{"android.permission.RECORD_AUDIO"}, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
