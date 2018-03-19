package com.reednit.android.network.json;

import com.reednit.android.model.Link;
import com.squareup.moshi.Json;

import java.util.List;

public class ListingJson {

    @Json(name = "kind")
    private String kind;
    @Json(name = "data")
    private ListingDataJson data;
    @Json(name = "before")
    private String before;
    @Json(name = "after")
    private String after;

//    public ContentValues[] toContentValuesArrayFromChildren(){
//        return data.toContentValuesArray();
//    }

    public List<Link> toModel(){
        return data.toModel();
    }

}
