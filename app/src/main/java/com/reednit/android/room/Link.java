package com.reednit.android.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "link")
public class Link {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;
    @ColumnInfo(name = "like_count")
    public int likeCount;
    @ColumnInfo(name = "is_self")
    public int isSelf;
    @ColumnInfo(name = "is_video")
    public int isVideo;
    @ColumnInfo(name = "selftext")
    public int selftext;
    @ColumnInfo(name = "selftext_html")
    public int selftextHtml;

}
