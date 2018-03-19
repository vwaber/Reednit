package com.reednit.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class Fetcher extends BroadcastReceiver{

    private final Context mContext;
    private final OnEventListener mListener;

    public interface OnEventListener {
        void onFetcherStatus(String status);
    }

    public Fetcher(Context context, OnEventListener listener){
        mContext = context;
        mListener = listener;
        IntentFilter intentFilter = new IntentFilter(FetcherIntentService.ACTION_BROADCAST);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(this, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mListener.onFetcherStatus(intent.getStringExtra(FetcherIntentService.EXTRA_STATUS));
    }

    public void fetchLinks(){
        String action = FetcherIntentService.ACTION_FETCH_LINKS;
        startService(action);
    }

    public void refreshLinks(){
        String action = FetcherIntentService.ACTION_REFRESH_LINKS;
        startService(action);
    }

    private void startService(String action){
        Intent serviceIntent = new Intent(mContext, FetcherIntentService.class);
        serviceIntent.setAction(action);
        mContext.startService(serviceIntent);
    }

}
