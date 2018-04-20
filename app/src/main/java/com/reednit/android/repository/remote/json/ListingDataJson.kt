package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class ListingDataJson(
        @Json(name = "children")
        var children: List<LinkJson>
)