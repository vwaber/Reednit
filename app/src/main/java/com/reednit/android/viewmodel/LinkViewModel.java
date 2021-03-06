package com.reednit.android.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.reednit.android.valueobject.Link;
import com.reednit.android.repository.Repository;

import java.util.List;

public class LinkViewModel extends AndroidViewModel {

    private final Repository mRepository;

    private final MutableLiveData<Link> selected = new MutableLiveData<>();

    public LinkViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public LiveData<List<Link>> getAll() {
        return  mRepository.getAllLinks();
    }

    public LiveData<Link> get(int uid) {
        return  mRepository.getLink(uid);
    }

    public void select(Link link) {
        selected.setValue(link);
    }

    public LiveData<Link> getSelected(){
        return selected;
    }

}
