package com.reednit.android.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.reednit.android.R;
import com.reednit.android.viewmodel.LinkViewModel;
import com.reednit.android.repository.local.Link;
import com.reednit.android.ui.activity.ReednitActivity;
import com.reednit.android.ui.recycler.LinkListAdapter;

public class MainActivity extends ReednitActivity implements LinkListAdapter.OnLinkClickListener {

    private LinkViewModel mLinkViewModel;

    private boolean mIsDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLinkViewModel = ViewModelProviders.of(this).get(LinkViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment =
                (MainFragment) fragmentManager.findFragmentById(R.id.fl_fragment_primary);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_primary, mainFragment)
                    .commit();
        }

        LinkDisplayFragment linkDisplayFragment = (LinkDisplayFragment) fragmentManager.findFragmentById(R.id.fl_fragment_secondary);
        View secondaryFragmentView = findViewById(R.id.fl_fragment_secondary);
        mIsDualPane = secondaryFragmentView != null && secondaryFragmentView.getVisibility() == View.VISIBLE;

        if (mIsDualPane && linkDisplayFragment == null) {
            linkDisplayFragment = new LinkDisplayFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_secondary, linkDisplayFragment)
                    .commit();
        }else if(linkDisplayFragment != null){
            fragmentManager.beginTransaction()
                    .remove(linkDisplayFragment)
                    .commit();
        }

    }

    @Override
    public void onLinkClick(Link link) {

        if(mIsDualPane){
            mLinkViewModel.select(link);
        }else {
            Bundle args = new Bundle();
            args.putInt(Link.EXTRA_LINK_UID, link.uid);
            Intent intent = new Intent(this, LinkDisplayActivity.class);
            intent.putExtras(args);
            startActivity(intent);
        }

    }
}
