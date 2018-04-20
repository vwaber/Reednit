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

//package com.reednit.android.repository.local;
//
//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//
//import com.reednit.android.repository.remote.json.LinkDataJson;
//
//@SuppressWarnings("WeakerAccess")
//@Entity(tableName = "link")
//public class Link {
//
//    public final static String EXTRA_LINK_UID = "com.reednit.android.room.LINK_UID";
//
//    @PrimaryKey(autoGenerate = true)
//    public int uid;
//
//    @ColumnInfo(name = "name")
//    public String name;
//    @ColumnInfo(name = "title")
//    public String title;
//    @ColumnInfo(name = "url")
//    public String url;
//    @ColumnInfo(name = "thumbnail")
//    public String thumbnail;
//    @ColumnInfo(name = "like_count")
//    public int likeCount;
//    @ColumnInfo(name = "is_self")
//    public boolean isSelf;
//    @ColumnInfo(name = "is_video")
//    public boolean isVideo;
//    @ColumnInfo(name = "selftext")
//    public String selftext;
//    @ColumnInfo(name = "selftext_html")
//    public String selftextHtml;
//
//    public Link(){}
//
//    public Link(LinkDataJson linkDataJson){
//        uid = 0;
//        name = linkDataJson.name;
//        title = linkDataJson.title;
//        thumbnail = linkDataJson.thumbnail;
//        url = linkDataJson.url;
//        isSelf = linkDataJson.isSelf;
//        isVideo = linkDataJson.isVideo;
//        selftext = linkDataJson.selftext;
//        selftextHtml = linkDataJson.selftextHtml;
//    }
//
//}
