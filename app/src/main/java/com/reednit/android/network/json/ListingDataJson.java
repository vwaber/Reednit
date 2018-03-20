package com.reednit.android.network.json;

import com.reednit.android.room.Link;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

class ListingDataJson {

    @Json(name = "children")
    private List<LinkJson> children = null;

    public List<LinkJson> getChildren() { return children; }

//    ContentValues[] toContentValuesArray() {
//
//        ContentValues[] result = new ContentValues[children.size()];
//
//        int index = 0;
//        for(LinkJson child : children){
//            result[index] = child.toContentValues();
////            result[index].put(LocalContract.LinkEntry.COLUMN_NAME_ORDINAL, index);
//            index ++;
//        }
//
//        return result;
//
//    }

    List<Link> toModel(){
        List<Link> result = new ArrayList<>();
        for(LinkJson child : children){
            Link model = child.toModel();
            if(model != null)
                result.add(child.toModel());
        }
        return result;
    }

}
