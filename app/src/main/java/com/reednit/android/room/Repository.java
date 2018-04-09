package com.reednit.android.room;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.reednit.android.network.NetworkClient;

import java.util.List;

public class Repository {

    private final NetworkClient mNetworkClient;

    private LinkDao mLinkDao;

    public Repository(Context context){
        mNetworkClient = NetworkClient.getInstance();
        AppDatabase db = AppDatabase.getDatabase(context);
        mLinkDao = db.linkDao();
    }

    public LiveData<List<Link>> getAllLinks(){
        return mLinkDao.getAll();
    }

    public LiveData<Link> getLink(int uid){
        return mLinkDao.get(uid);
    }

    public boolean fetchFreshLinks(){
        List<Link> links = mNetworkClient.fetchLinks(null);
        if(links == null || links.size() <= 0) return false;
        mLinkDao.deleteAll();
        mLinkDao.insert(links);
        return true;
    }

    public boolean fetchAdditionalLinks(){
        List<Link> links = mNetworkClient.fetchLinks(getLastLinkName());
        if(links == null || links.size() <= 0) return false;
        mLinkDao.insert(links);
        return true;
    }

    private String getLastLinkName(){
        Link lastLink = mLinkDao.getLast();
        return lastLink.name;
    }

}