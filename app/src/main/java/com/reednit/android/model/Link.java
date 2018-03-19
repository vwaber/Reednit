package com.reednit.android.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.reednit.android.network.json.LinkDataJson;
import com.reednit.android.persistence.LocalContract;

import java.util.ArrayList;
import java.util.List;

public class Link implements Parcelable{

    public final static String EXTRA_LINK = "com.reednit.android.model.LINK";

    public final String name;

    public final String title;
    public final String thumbnail;
    public final String url;
    public final long createdUtc;
    public final boolean isSelf;
    public final boolean isVideo;
    public final String selftext;
    public final String selftextHtml;

    //TODO: Update this so that proper changes are reflected on refresh (DiffUtil.calculateDiff)
    @Override
    public boolean equals(Object o){
        return getClass() == o.getClass();
    }

    public Link(Cursor cursor){
        name = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_NAME));
        title = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_TITLE));
        thumbnail = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_THUMBNAIL));
        url = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_URL));
        createdUtc = cursor.getLong(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_CREATED_UTC));
        isSelf = (cursor.getInt(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_IS_SELF)) != 0);
        isVideo = (cursor.getInt(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_IS_VIDEO)) != 0);
        selftext = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_SELFTEXT));
        selftextHtml = cursor.getString(cursor.getColumnIndex(LocalContract.LinkEntry.COLUMN_NAME_SELFTEXT_HTML));
    }

    protected Link(Parcel in) {
        name = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        url = in.readString();
        createdUtc = in.readLong();
        isSelf = in.readByte() != 0;
        isVideo = in.readByte() != 0;
        selftext = in.readString();
        selftextHtml = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(url);
        parcel.writeLong(createdUtc);
        parcel.writeByte((byte) (isSelf ? 1 : 0));
        parcel.writeByte((byte) (isVideo ? 1 : 0));
        parcel.writeString(selftext);
        parcel.writeString(selftextHtml);
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) { return new Link(in); }
        @Override
        public Link[] newArray(int size) { return new Link[size]; }
    };

    public Link(LinkDataJson linkDataJson){
        name = linkDataJson.name;
        title = linkDataJson.title;
        thumbnail = linkDataJson.thumbnail;
        url = linkDataJson.url;
        createdUtc = linkDataJson.createdUtc;
        isSelf = linkDataJson.isSelf;
        isVideo = linkDataJson.isVideo;
        selftext = linkDataJson.selftext;
        selftextHtml = linkDataJson.selftextHtml;
    }

    public static List<Link> listFromCursor(Cursor cursor){
        int position = cursor.getPosition();
        List<Link> result = new ArrayList<>();
        while(cursor.moveToNext()){
            result.add(new Link(cursor));
        }
        cursor.moveToPosition(position);
        return result;
    }

    private ContentValues toContentValues(){
        ContentValues result = new ContentValues();
        result.put(LocalContract.LinkEntry.COLUMN_NAME_NAME, name);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_TITLE, title);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_URL, url);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_THUMBNAIL, thumbnail);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_CREATED_UTC, createdUtc);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_IS_SELF, isSelf);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_IS_VIDEO, isVideo);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_SELFTEXT, selftext);
        result.put(LocalContract.LinkEntry.COLUMN_NAME_SELFTEXT_HTML, selftextHtml);
        return result;
    }

    public static ContentValues[] toContentValuesArray(List<Link> linkList){
        ContentValues[] result = new ContentValues[linkList.size()];
        int index = 0;
        for(Link link : linkList){
            result[index] = link.toContentValues();
            index ++;
        }
        return result;
    }

}
