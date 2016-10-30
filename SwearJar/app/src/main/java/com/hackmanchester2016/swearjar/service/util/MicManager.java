package com.hackmanchester2016.swearjar.service.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.List;

import static android.speech.SpeechRecognizer.RESULTS_RECOGNITION;

/**
 * Created by tomr on 29/10/2016.
 */

public class MicManager {

    private static final String TAG = "MicManager";

    private Context mContext;
    private SpeechRecognizer mSpeechRecognizer;

    private Intent mIntent;
    private boolean stopClicked = false;

    public interface VoiceDetectionResultListener {
        void onResult(String result);
        void onPartialResult(String result);
    }

    private VoiceDetectionResultListener mListener = null;

    private RecognitionListener mRecognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int i) {
            Log.d(TAG, "onError");
            if(!stopClicked) {
                startListening();
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            Log.d(TAG, "onResults");
            List<String> results = bundle.getStringArrayList(RESULTS_RECOGNITION);

            if(mListener != null && results != null && results.size() > 0) {
                mListener.onResult(results.get(0));
            }

            if(!stopClicked) {
                startListening();
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d(TAG, "onPartialResults");
            List<String> results = bundle.getStringArrayList(RESULTS_RECOGNITION);

            if(mListener != null && results != null && results.size() > 0) {
                mListener.onPartialResult(results.get(0));
            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
        }
    };

    public MicManager(Context context, VoiceDetectionResultListener listener) {
        mContext = context;
        mListener = listener;
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        mSpeechRecognizer.setRecognitionListener(mRecognitionListener);

        mIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                context.getPackageName());
        mIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Integer.MAX_VALUE);
        mIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Integer.MAX_VALUE);
        mIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
        mIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
    }


    public void startListening() {
        stopClicked = false;
        mSpeechRecognizer.startListening(mIntent);
    }

    public void stopListening() {
        stopClicked = true;
        mSpeechRecognizer.stopListening();
    }

    public void destroy() {
        mSpeechRecognizer.destroy();
    }
}
