package com.reednit.android.ui.recycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    protected abstract void requestLoad();

    private final LinearLayoutManager mLayoutManager;

    private final int mThreshold;
    private int mTotalCount;
    private boolean mIsLoading = false;

    protected EndlessScrollListener(LinearLayoutManager layoutManager, int threshold){
        mLayoutManager = layoutManager;
        mThreshold = threshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        if(dy <= 0)
            return;
        else if(mIsLoading && mTotalCount < mLayoutManager.getItemCount())
            mIsLoading = false;
        else if(mIsLoading)
            return;

        mTotalCount = mLayoutManager.getItemCount();
        int lastVisible = mLayoutManager.findLastVisibleItemPosition();

        if(lastVisible + mThreshold >= mTotalCount){
            mIsLoading = true;
            requestLoad();
        }

    }

    public void reset(){
        mIsLoading = false;
        mTotalCount = 0;
    }

}
