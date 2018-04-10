package com.reednit.android.repository.remote;

import android.support.annotation.NonNull;

import com.reednit.android.repository.remote.json.AuthTokenJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

class AuthInterceptor implements Interceptor{

    private static final String AUTH_URL = "https://www.reddit.com/api/v1/access_token";

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        Request.Builder builder;

        if(response.code() == 403){

            Request.Builder tokenBuilder = new Request.Builder();
            tokenBuilder.url(AUTH_URL);

            setAuthHeader(tokenBuilder);
            setAuthPost(tokenBuilder);

            Response tokenResponse = chain.proceed(tokenBuilder.build());

            ResponseBody tokenBody = tokenResponse.body();

            if(tokenBody != null){
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<AuthTokenJson> adapter = moshi.adapter(AuthTokenJson.class);
                AuthTokenJson authToken = adapter.fromJson(tokenBody.string());

                if(authToken != null){
                    builder = request.newBuilder();
                    builder.header(
                            "Authorization",
                            "Bearer " + authToken.getToken()
                    );

                    return chain.proceed(builder.build());
                }
            }
        }
        return response;
    }

    private void setAuthHeader(Request.Builder builder){
        builder.header(
                "Authorization",
                Credentials.basic("pheWpomhF7Mm3Q", "")
        );
    }

    private void setAuthPost(Request.Builder builder){

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "https://oauth.reddit.com/grants/installed_client")
                .add("device_id", UUID.randomUUID().toString())
                .build();

        builder.post(formBody);

    }

}
