package com.reednit.android.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.reednit.android.network.json.LinkDataJson;

@Entity(tableName = "link")
public class Link {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "name")
    public String name;
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
    public String selftext;
    @ColumnInfo(name = "selftext_html")
    public String selftextHtml;

    public Link(){}

    public Link(LinkDataJson linkDataJson){
        name = linkDataJson.name;
        title = linkDataJson.title;
        thumbnail = linkDataJson.thumbnail;
        url = linkDataJson.url;
        isSelf = boolToInt(linkDataJson.isSelf);
        isVideo = boolToInt(linkDataJson.isVideo);
        selftext = linkDataJson.selftext;
        selftextHtml = linkDataJson.selftextHtml;
    }

    private int boolToInt(Boolean bool){
        return (bool) ? 1 : 0;
    }

}
