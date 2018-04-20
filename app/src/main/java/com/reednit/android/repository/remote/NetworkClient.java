package com.reednit.android.repository.remote;

import android.util.Log;

import com.reednit.android.repository.local.Link;
import com.reednit.android.repository.remote.adapter.NullStringAdapter;
import com.reednit.android.repository.remote.json.LinkJson;
import com.reednit.android.repository.remote.json.ListingJson;
import com.squareup.moshi.KotlinJsonAdapterFactory;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkClient {

    private static final String BASE_URL = "https://oauth.reddit.com";

    private static final String LOG_TAG = NetworkClient.class.getName();
    private static final String LOG_MSG = "Error occurred retrieving links from server.";

    private static NetworkClient mInstance = null;

    private static Retrofit mRetrofit;


    private NetworkClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .build();

        Moshi moshi = new Moshi.Builder()
                .add(new NullStringAdapter())
                .add(new KotlinJsonAdapterFactory())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    public static NetworkClient getInstance() {
        if (mInstance == null) mInstance = new NetworkClient();
        return mInstance;
    }

    public List<Link> fetchLinks(String after){

        List<Link> result;
        ListingJson listingJson;
        RedditService service = mRetrofit.create(RedditService.class);
        Call<ListingJson> call = service.getListing(after);

        try {
            listingJson = call.execute().body();
        } catch (IOException e) {
            Log.e(LOG_TAG, LOG_MSG);
            return null;
        }

        if(listingJson == null) return null;

//        result = listingJson.toModel();

        List<LinkJson> children = listingJson.getData().getChildren();
        List<Link> links = new ArrayList<>();

        for(LinkJson json: children){
            links.add(new Link(json.getData()));
        }

        return links;

    }

}