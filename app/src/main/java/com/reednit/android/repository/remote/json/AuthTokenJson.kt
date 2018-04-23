package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class AuthTokenJson(
        @Json(name = "access_token")
        val token: String,
        @Json(name = "token_type")
        val type: String,
        @Json(name = "expires_in")
        val expiresIn: Int,
        @Json(name = "scope")
        val scope: String
)