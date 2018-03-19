package com.reednit.android.network.json;

import com.squareup.moshi.Json;

public class LinkDataJson {

    public LinkDataJson() {}

    @Json(name = "name")
    public String name;
    @Json(name = "subreddit")
    public String subreddit;
    @Json(name = "title")
    public String title;
    @Json(name = "thumbnail")
    public String thumbnail;
    @Json(name = "url")
    public String url;
    @Json(name = "author")
    public String author;
    @Json(name = "created_utc")
    public long createdUtc;
    @Json(name = "is_self")
    public boolean isSelf;
    @Json(name = "is_video")
    public boolean isVideo;
    @Json(name = "selftext")
    public String selftext;
    @Json(name = "selftext_html")
    public String selftextHtml;


}