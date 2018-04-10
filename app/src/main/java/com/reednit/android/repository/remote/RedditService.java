package com.reednit.android.repository.remote;

import com.reednit.android.repository.remote.json.ListingJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RedditService {
    @GET("/r/all")
    Call<ListingJson> getListing(@Query("after") String after);
}
