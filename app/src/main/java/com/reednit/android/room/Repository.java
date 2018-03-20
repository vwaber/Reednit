package com.reednit.android.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.reednit.android.network.NetworkClient;

import java.util.List;

public class Repository {

    private final NetworkClient mNetworkClient;

    private LinkDao mLinkDao;
    private LiveData<List<Link>> mAllLinks;

    public Repository(Context context){
        mNetworkClient = NetworkClient.getInstance();
        AppDatabase db = AppDatabase.getDatabase(context);
        mLinkDao = db.linkDao();
        mAllLinks = mLinkDao.getAll();
    }

    public LiveData<List<Link>> getAllLinks(){
        return mAllLinks;
    }

    public boolean fetchFreshLinks(){
        List<Link> links = mNetworkClient.fetchLinks(null);
        if(links == null || links.size() <= 0) return false;
        mLinkDao.deleteAll();
        mLinkDao.insert(links.toArray(new Link[links.size()]));
        return true;
    }

    public boolean fetchAdditionalLinks(){
        List<Link> links = mNetworkClient.fetchLinks(getLastLinkName());
        if(links == null || links.size() <= 0) return false;
        mLinkDao.insert(links.toArray(new Link[links.size()]));
        return true;
    }

    private String getLastLinkName(){
        Link lastLink = mLinkDao.getLast();
        return lastLink.name;
    }

}
