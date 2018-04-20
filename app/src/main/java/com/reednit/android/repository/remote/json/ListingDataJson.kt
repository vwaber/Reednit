package com.reednit.android.repository.remote.json

import com.squareup.moshi.Json

data class ListingDataJson(
        @Json(name = "children")
        var children: List<LinkJson>
)

//package com.reednit.android.repository.remote.json;
//
//import com.reednit.android.repository.local.Link;
//import com.squareup.moshi.Json;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SuppressWarnings("unused")
//class ListingDataJson {
//
//    @Json(name = "children")
//    private List<LinkJson> children = null;
//
//    public List<LinkJson> getChildren() { return children; }
//
//    List<Link> toModel(){
//        List<Link> result = new ArrayList<>();
//        for(LinkJson child : children){
//            Link model = child.toModel();
//            if(model != null)
//                result.add(child.toModel());
//        }
//        return result;
//    }
//
//}
