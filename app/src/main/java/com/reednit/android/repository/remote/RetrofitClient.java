package com.reednit.android.repository.remote;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

class RetrofitClient {

    private static final String BASE_URL = "https://oauth.reddit.com";

    private static Retrofit mInstance = null;

    private RetrofitClient(){}

    public static Retrofit getInstance(){

        if(mInstance == null){

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(1);

            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor())
                    .build();

            mInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }

        return mInstance;

    }

}