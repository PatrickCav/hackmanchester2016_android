package com.hackmanchester2016.swearjar.ui.setup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.SetupActivity;
import com.hackmanchester2016.swearjar.engine.Engine;

/**
 * Created by dant on 29/10/2016.
 */

public class SetupFragmentLocation extends Fragment {

    private Button signupButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_location, container, false);

        signupButton = (Button) view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(signupButtonListener);

        return view;
    }

    private Button.OnClickListener signupButtonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(getActivity() instanceof SetupActivity) {
                Engine.getInstance().getSetupController().setupComplete();
                ((SetupActivity)getActivity()).setupComplete();
            }
        }
    };

}
