package com.reednit.android.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {

    private LinkDao mLinkDao;
    private LiveData<List<Link>> mAllLinks;

    public Repository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mLinkDao = db.linkDao();
        mAllLinks = mLinkDao.getAll();
    }

    public LiveData<List<Link>> getAllLinks(){
        return mAllLinks;
    }

    public void insert (Link link) {
        new insertAsyncTask(mLinkDao).execute(link);
    }

    private static class insertAsyncTask extends AsyncTask<Link, Void, Void> {

        private LinkDao mAsyncTaskDao;

        insertAsyncTask(LinkDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Link... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
