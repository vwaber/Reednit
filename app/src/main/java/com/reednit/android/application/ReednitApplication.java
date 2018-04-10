package com.reednit.android.application;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

public class ReednitApplication extends Application{

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
