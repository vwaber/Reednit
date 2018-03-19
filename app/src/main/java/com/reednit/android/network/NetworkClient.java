package com.reednit.android.network;

import android.util.Log;

import com.reednit.android.model.Link;
import com.reednit.android.network.json.ListingJson;

import java.io.IOException;
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

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
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

        result = listingJson.toModel();

        return result;

    }

}