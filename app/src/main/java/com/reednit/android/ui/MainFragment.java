package com.reednit.android.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.reednit.android.R;
import com.reednit.android.viewmodel.LinkViewModel;
import com.reednit.android.repository.local.Link;
import com.reednit.android.service.Fetcher;
import com.reednit.android.service.FetcherIntentService;
import com.reednit.android.ui.recycler.EndlessScrollListener;
import com.reednit.android.ui.recycler.LinkListAdapter;

import java.util.List;

public class MainFragment extends Fragment
        implements
        SwipeRefreshLayout.OnRefreshListener,
        Fetcher.OnEventListener {

    private Fetcher mFetcher;

    private Snackbar mFetchingSnackbar;
    private Snackbar mRefreshFailureSnackbar;
    private Snackbar mFetchFailureSnackbar;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinkListAdapter mAdapter;
    private EndlessScrollListener mScrollListener;
    private RecyclerView mRecyclerView;

    private boolean mIsRefreshing = false;

    public MainFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFetcher = new Fetcher(getContext(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_update:
                refreshOperation();
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() == null) return;

        LinkViewModel linkViewModel = ViewModelProviders.of(getActivity()).get(LinkViewModel.class);
        linkViewModel.getAll().observe(this, new Observer<List<Link>>() {
            @Override
            public void onChanged(@Nullable List<Link> links) {
                onDataUpdate(links);
            }
        });


        if(mRecyclerView.getAdapter() != null){
            mAdapter = (LinkListAdapter) mRecyclerView.getAdapter();
        }else{
            mAdapter = new LinkListAdapter((LinkListAdapter.OnLinkClickListener) getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }

        CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.cl_fragment);

        mFetchingSnackbar = Snackbar.make(coordinatorLayout,
                R.string.snackbar_fetching_links,
                Snackbar.LENGTH_SHORT);

        mRefreshFailureSnackbar = Snackbar.make(coordinatorLayout,
                R.string.snackbar_refresh_links_failure,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_retry_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshOperation();
                    }
                });

        mFetchFailureSnackbar = Snackbar.make(coordinatorLayout,
                R.string.snackbar_fetching_links_failure,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_retry_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchOperation();
                    }
                });


        mScrollListener = new EndlessScrollListener(
                (LinearLayoutManager) mRecyclerView.getLayoutManager(),
                getResources().getInteger(R.integer.link_scroller_threshold)) {
            @Override
            protected void requestLoad() {
                mFetchingSnackbar.show();
                fetchOperation();
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);

//        getLoaderManager().initLoader(0, null, this);
    }

    private void onDataUpdate(List<Link> links){

        mAdapter.update(links);

        mFetchFailureSnackbar.dismiss();
        mRefreshFailureSnackbar.dismiss();

        if(mIsRefreshing){
            mRecyclerView.smoothScrollToPosition(0);
            mIsRefreshing = false;
        }

        mScrollListener.reset();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        refreshOperation();
    }

    private void fetchOperation(){
        mFetcher.fetchLinks();
    }

    private void refreshOperation(){
        mRefreshFailureSnackbar.dismiss();
        mSwipeRefreshLayout.setRefreshing(true);
        mFetchFailureSnackbar.dismiss();
        mIsRefreshing = true;
        mFetcher.refreshLinks();
    }

    @Override
    public void onFetcherStatus(String status) {

        mSwipeRefreshLayout.setRefreshing(false);

        switch(status){
            case FetcherIntentService.STATUS_REFRESH_FAILURE:
                mIsRefreshing = false;
                mRefreshFailureSnackbar.show();
                break;
            case FetcherIntentService.STATUS_FETCH_FAILURE:
                mFetchFailureSnackbar.show();
                break;
            default:
                break;
        }

    }

}
