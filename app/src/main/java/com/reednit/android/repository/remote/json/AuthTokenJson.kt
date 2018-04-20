package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class AuthTokenJson(
        @Json(name = "access_token")
        var token: String,
        @Json(name = "token_type")
        var type: String,
        @Json(name = "expires_in")
        var expiresIn: String,
        @Json(name = "scope")
        var scope: String
)

//package com.reednit.android.repository.remote.json;
//
//
//import com.squareup.moshi.Json;
//
//@SuppressWarnings("unused")
//public class AuthTokenJson {
//
//    @Json(name = "access_token")
//    private String token;
//    @Json(name = "token_type")
//    private String type;
//    @Json(name = "expires_in")
//    private String expiresIn;
//    @Json(name = "scope")
//    private String scope;
//
//    public String getToken() {
//        return token;
//    }
//}