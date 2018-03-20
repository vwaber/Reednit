package com.reednit.android.arch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.reednit.android.room.Link;
import com.reednit.android.room.Repository;

import java.util.List;

public class LinkViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Link>> mAllLinks;

    public LinkViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllLinks = mRepository.getAllLinks();
    }

    public LiveData<List<Link>> getAllLinks() { return  mAllLinks; }

}
