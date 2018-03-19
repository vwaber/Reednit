package com.reednit.android.network;

import com.reednit.android.network.json.ListingJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RedditService {
    @GET("/r/all")
    Call<ListingJson> getListing(@Query("after") String after);
}
