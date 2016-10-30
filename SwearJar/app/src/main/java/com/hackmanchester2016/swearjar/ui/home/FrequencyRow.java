package com.hackmanchester2016.swearjar.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hackmanchester2016.swearjar.R;

/**
 * Created by patrickc on 30/10/2016
 */
public class FrequencyRow extends FrameLayout {

    public FrequencyRow(Context context, String word, String frequency) {
        super(context);
        init(word, frequency);
    }

    private void init(String word, String frequency) {
        View.inflate(getContext(), R.layout.frequency_row, this);

        ((TextView) findViewById(R.id.first)).setText(word);
        ((TextView) findViewById(R.id.second)).setText(frequency);
    }

}
