package com.reednit.android.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "link")
public class Link {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "thumbnail")
    private String thumbnail;
    @ColumnInfo(name = "like_count")
    private int likeCount;
    @ColumnInfo(name = "is_self")
    private int isSelf;
    @ColumnInfo(name = "is_video")
    private int isVideo;
    @ColumnInfo(name = "selftext")
    private int selftext;
    @ColumnInfo(name = "selftext_html")
    private int selftextHtml;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public int getSelftext() {
        return selftext;
    }

    public int getSelftextHtml() {
        return selftextHtml;
    }

}
