package com.reednit.android.repository.remote

import com.reednit.android.repository.local.Link
import com.reednit.android.repository.remote.adapter.NullStringAdapter
import com.reednit.android.repository.remote.json.LinkJson
import com.reednit.android.repository.remote.json.ListingJson
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

object NetworkClient{

    private const val BASE_URL: String = "https://oauth.reddit.com"

    val moshi: Moshi = Moshi.Builder()
            .add(NullStringAdapter())
            .add(KotlinJsonAdapterFactory())
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

        val call: Call<ListingJson> = mService.getListing(after)

        val listingJson: ListingJson = try {
            call.execute().body()!!
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }

        val children: List<LinkJson> = listingJson.data.children
        val links: MutableList<Link> = ArrayList()

        for(json in children){
            links.add(Link(json.data))
        }

        return links
    }

}