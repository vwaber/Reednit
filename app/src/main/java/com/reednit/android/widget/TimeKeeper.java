package com.reednit.android.widget;

import android.content.Context;
import android.content.SharedPreferences;

public class TimeKeeper {

    private static final String PREFERENCE_FILE_KEY = "com.reednit.android.widget.PREFERENCE_FILE_KEY";
    private static final String USAGE_TIME_PREFERENCE_KEY = "com.reednit.android.widget.USAGE_TIME_PREFERENCE_KEY";

    private static TimeKeeper mInstance = null;

    private long mStartTime;
    private boolean mHasStarted = false;

    private TimeKeeper(){}

    public static TimeKeeper getInstance() {
        if (mInstance == null) mInstance = new TimeKeeper();
        return mInstance;
    }

    public void start(){
        if(mHasStarted) return;
        mStartTime = System.currentTimeMillis();
        mHasStarted = true;
    }

    public void stop(Context context){
        if(!mHasStarted) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFERENCE_FILE_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        long savedTime = sharedPreferences.getLong(USAGE_TIME_PREFERENCE_KEY, 0);
        savedTime += (System.currentTimeMillis() - mStartTime);

        editor.putLong(USAGE_TIME_PREFERENCE_KEY, savedTime);
        editor.apply();
        mHasStarted = false;
    }

    long getTime(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFERENCE_FILE_KEY,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(USAGE_TIME_PREFERENCE_KEY, 0);
    }

}
