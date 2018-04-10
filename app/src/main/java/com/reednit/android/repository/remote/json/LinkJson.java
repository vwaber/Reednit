package com.reednit.android.repository.remote.json;

import android.text.TextUtils;
import android.util.Log;

import com.reednit.android.repository.local.Link;
import com.squareup.moshi.Json;

@SuppressWarnings("unused")
class LinkJson {

    private static final String LOG_TAG = LinkJson.class.getName();
    private static final String LOG_MSG = "Discarded improperly formatted link received from server.";

    @Json(name = "data")
    private LinkDataJson data;

    Link toModel(){
        if(TextUtils.isEmpty(data.name) || TextUtils.isEmpty(data.title) || TextUtils.isEmpty(data.url)){
            Log.w(LOG_TAG, LOG_MSG);
            return null;
        }

        return new Link(data);
    }

}
