package com.reednit.android.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import com.reednit.android.widget.TimeKeeper;
import com.reednit.android.widget.TimeKeeperWidgetProvider;

@SuppressLint("Registered")
public class ReednitActivity extends AppCompatActivity {

    private TimeKeeper mTimeKeeper;

    @Override
    protected void onResume() {
        super.onResume();
        mTimeKeeper = TimeKeeper.getInstance();
        mTimeKeeper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTimeKeeper = TimeKeeper.getInstance();
        mTimeKeeper.stop(this);
        TimeKeeperWidgetProvider.forceUpdate(this);
    }

}
