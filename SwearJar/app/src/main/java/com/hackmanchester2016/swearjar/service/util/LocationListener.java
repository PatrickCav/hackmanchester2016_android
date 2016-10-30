package com.hackmanchester2016.swearjar.service.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SendLocationRequest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomr on 30/10/2016.
 */

public class LocationListener implements android.location.LocationListener {
    private static final String TAG = "LOCATIONLISTENER";

    private Location mLastLocation;
    private Geocoder mGeoCoder;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    public LocationListener(String provider, Context context, GoogleApiClient googleApiClient) {
        Log.e(TAG, "LocationListener " + provider);
        mLastLocation = new Location(provider);
        mGeoCoder = new Geocoder(context);
        mContext = context;
        mGoogleApiClient = googleApiClient;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onLocationChanged(final Location location) {
        Log.e(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);

        Address address;
        try {
            address = mGeoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Log.d("LOCATION", "Address: " + address.getAddressLine(0));

        final Address finalAddress = address;

        // Now we try to get a 'place' for the location
        if(mGoogleApiClient.isConnected()) {
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                    .getCurrentPlace(mGoogleApiClient, null);

            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    PlaceLikelihood mostLikely = null;
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        Log.d(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        if(mostLikely == null || placeLikelihood.getLikelihood() > mostLikely.getLikelihood()) {
                            mostLikely = placeLikelihood;
                        }
                    }

                    String placeName = mostLikely == null ? null : mostLikely.getPlace().getName().toString();
                    List<Integer> placeTypes = mostLikely.getPlace().getPlaceTypes();

                    for(int p : placeTypes) {
                        if(p == Place.TYPE_BAR || p == Place.TYPE_GYM) {
                            String placeType = p == Place.TYPE_BAR ? "Bar" : "Gym";
                            SendLocationRequest req = new SendLocationRequest(placeName, placeType);
                            sendLocation(req);
                            break;
                        }
                    }

                    likelyPlaces.release();
                }
            });
        } else {
        }
    }

    private void sendLocation(SendLocationRequest req) {
        Engine.getInstance().getRetrofitClient().getApi().sendLocation(req).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "SUCCESS");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "FAIL");
            }
        });
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG, "onProviderDisabled: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG, "onStatusChanged: " + provider);
    }
}
