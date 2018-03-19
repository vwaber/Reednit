package com.reednit.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.reednit.android.persistence.Repository;

public class FetcherIntentService extends IntentService {

    public static final String ACTION_FETCH_LINKS = "com.reednit.android.service.FETCH_LINKS";
    public static final String ACTION_REFRESH_LINKS = "com.reednit.android.service.REFRESH_LINKS";
    public static final String ACTION_BROADCAST = "com.reednit.android.service.BROADCAST";

    public static final String EXTRA_STATUS = "com.reednit.android.service.STATUS";
    public static final String STATUS_SUCCESS = "com.reednit.android.service.SUCCESS";
    public static final String STATUS_FETCH_FAILURE = "com.reednit.android.service.FETCH_FAILURE";
    public static final String STATUS_REFRESH_FAILURE = "com.reednit.android.service.REFRESH_FAILURE";

    public FetcherIntentService() {
        super("FetcherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;

        final Repository repository = new Repository(this);

        String status = STATUS_SUCCESS;

        switch (action) {
            case ACTION_REFRESH_LINKS:
                if(!repository.fetchFreshLinks()) status = STATUS_REFRESH_FAILURE;
                break;
            case ACTION_FETCH_LINKS:
                if(!repository.fetchAdditionalLinks()) status = STATUS_FETCH_FAILURE;
                break;
            default:
                break;
        }

        reportStatus(status);

    }

    private void reportStatus(String status) {
        Intent intent = new Intent(ACTION_BROADCAST).putExtra(EXTRA_STATUS, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}