package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class ListingJson(
        @Json(name = "data")
        var data: ListingDataJson
)