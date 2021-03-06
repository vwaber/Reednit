package com.reednit.android.repository.remote

import com.reednit.android.valueobject.Link
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("/r/all")
    fun getListing(@Query("after") after: String): Call<List<Link>>
}