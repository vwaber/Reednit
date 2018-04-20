package com.reednit.android.repository.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.reednit.android.repository.remote.json.LinkDataJson

const val EXTRA_LINK_UID: String = "com.reednit.android.room.LINK_UID"

@Entity(tableName = "link")
data class Link(

        @PrimaryKey(autoGenerate = true)
        val uid: Int,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "url")
        val url: String,

        @ColumnInfo(name = "thumbnail")
        val thumbnail: String,

        @ColumnInfo(name = "is_self")
        val isSelf: Boolean,

        @ColumnInfo(name = "selftext")
        val selftext: String,

        @ColumnInfo(name = "selftext_html")
        val selftextHtml: String

) {

    constructor(data: LinkDataJson) : this(
            0,
            data.name,
            data.title,
            data.url,
            data.thumbnail,
            data.isSelf,
            data.selftext,
            data.selftextHtml
    )

}