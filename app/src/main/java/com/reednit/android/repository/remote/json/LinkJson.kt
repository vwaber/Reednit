package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class LinkJson(
        @Json(name = "data")
        val data: LinkDataJson
)