package com.reednit.android.repository.remote.json;

import com.reednit.android.repository.local.Link;
import com.squareup.moshi.Json;

import java.util.List;

@SuppressWarnings("unused")
public class ListingJson {

    @Json(name = "kind")
    private String kind;
    @Json(name = "data")
    private ListingDataJson data;
    @Json(name = "before")
    private String before;
    @Json(name = "after")
    private String after;

    public List<Link> toModel(){
        return data.toModel();
    }

}
