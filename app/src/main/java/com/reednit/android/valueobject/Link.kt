package com.reednit.android.valueobject

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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

)