package com.reednit.android.repository.remote

import com.reednit.android.valueobject.Link
import com.reednit.android.repository.remote.adapter.ListingJsonAdapter
import com.reednit.android.repository.remote.adapter.NullStringAdapter
import com.reednit.android.repository.remote.interceptor.AuthInterceptor
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkClient{

    private const val BASE_URL: String = "https://oauth.reddit.com"

    val moshi: Moshi = Moshi.Builder()
            .add(NullStringAdapter())
            .add(KotlinJsonAdapterFactory())
            .add(ListingJsonAdapter())
            .build()

    private val mHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

    private val mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    private val mService: RedditService = mRetrofit.create(RedditService::class.java)

    fun fetchLinks(after: String): List<Link> {

        val call: Call<List<Link>> = mService.getListing(after)
        return call.execute().body()!!

    }

}