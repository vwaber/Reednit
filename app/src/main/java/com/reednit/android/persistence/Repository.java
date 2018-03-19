package com.reednit.android.persistence;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.reednit.android.model.Link;
import com.reednit.android.network.NetworkClient;

import java.util.List;

public class Repository {

    private final ContentResolver mContentResolver;
    private final NetworkClient mNetworkClient;

    private final Uri mLinksContentUri = LocalContract.LinkEntry.CONTENT_URI;

    public Repository(Context context) {
        mContentResolver = context.getContentResolver();
        mNetworkClient = NetworkClient.getInstance();
    }

    public boolean fetchFreshLinks(){
        List<Link> links = mNetworkClient.fetchLinks(null);
        if(links == null || links.size() <= 0) return false;
        mContentResolver.delete(mLinksContentUri, null, null);
        mContentResolver.bulkInsert(mLinksContentUri, Link.toContentValuesArray(links));
        return true;
    }

    public boolean fetchAdditionalLinks(){
        List<Link> links = mNetworkClient.fetchLinks(getLastLinkName());
        if(links == null || links.size() <= 0) return false;
        mContentResolver.bulkInsert(mLinksContentUri, Link.toContentValuesArray(links));
        return true;
    }

    private String getLastLinkName(){
        Uri linksContentUri = LocalContract.LinkEntry.CONTENT_URI;
        Cursor cursor = mContentResolver.query(linksContentUri, null, null, null, null);
        if(cursor == null || cursor.getCount() == 0){
            return null;
        }
        cursor.moveToLast();
        Link link = new Link(cursor);
        cursor.close();
        return link.name;
    }

}
