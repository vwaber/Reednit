package com.reednit.android.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.reednit.android.repository.local.AppDatabase;
import com.reednit.android.repository.local.Link;
import com.reednit.android.repository.local.LinkDao;
import com.reednit.android.repository.remote.NetworkClient;

import java.util.List;

public class Repository {

    private final NetworkClient mNetworkClient;

    private final LinkDao mLinkDao;

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
