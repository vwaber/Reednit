package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class LinkJson(
        @Json(name = "data")
        var data: LinkDataJson
)

//import com.squareup.moshi.Json
//
//private val LOG_TAG: String? = LinkJson::class.simpleName
//private const val LOG_MSG: String = "Discarded improperly formatted link received from server."
//
//class LinkJson {
//
//    @Json(name = "data")
//    val data: LinkDataJson?
//
//}

//package com.reednit.android.repository.remote.json;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.reednit.android.repository.local.Link;
//import com.squareup.moshi.Json;
//
//@SuppressWarnings("unused")
//class LinkJson {
//
//    private static final String LOG_TAG = LinkJson.class.getName();
//    private static final String LOG_MSG = "Discarded improperly formatted link received from server.";
//
//    @Json(name = "data")
//    private LinkDataJson data;
//
//    Link toModel(){
//        if(TextUtils.isEmpty(data.getName()) || TextUtils.isEmpty(data.getTitle()) || TextUtils.isEmpty(data.getUrl())){
//            Log.w(LOG_TAG, LOG_MSG);
//            return null;
//        }
//
//        return new Link(data);
//    }
//
//}
