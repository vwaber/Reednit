package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class LinkDataJson(

        @Json(name = "name")
        val name: String,
        @Json(name = "subreddit")
        val subreddit: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "thumbnail")
        val thumbnail: String,
        @Json(name = "url")
        val url: String,
        @Json(name = "author")
        val author: String,
        @Json(name = "created_utc")
        val createdUtc: Long,
        @Json(name = "is_self")
        val isSelf: Boolean,
        @Json(name = "is_video")
        val isVideo: Boolean,
        @Json(name = "selftext")
        val selftext: String,
        @Json(name = "selftext_html")
        val selftextHtml: String

)