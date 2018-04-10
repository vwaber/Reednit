package com.reednit.android.repository.remote.json;


import com.squareup.moshi.Json;

public class AuthTokenJson {

    @Json(name = "access_token")
    private String token;
    @Json(name = "token_type")
    private String type;
    @Json(name = "expires_in")
    private String expiresIn;
    @Json(name = "scope")
    private String scope;

    public String getToken() {
        return token;
    }
}
