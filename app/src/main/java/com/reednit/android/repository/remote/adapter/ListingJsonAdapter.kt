package com.reednit.android.repository.remote.adapter

import com.reednit.android.valueobject.Link
import com.reednit.android.repository.remote.json.LinkJson
import com.reednit.android.repository.remote.json.ListingJson
import com.squareup.moshi.FromJson

class ListingJsonAdapter{

    @SuppressWarnings("unused")
    @FromJson
    fun listingFromJson(listingJson: ListingJson): List<Link> {
        val links: MutableList<Link> = ArrayList()
        val children: List<LinkJson> = listingJson.data.children
        for(json in children){
            links.add(json.data.toLink())
        }
        return links
    }

}