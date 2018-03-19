package com.reednit.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.reednit.android.R;
import com.reednit.android.model.Link;
import com.reednit.android.ui.activity.ReednitActivity;
import com.reednit.android.ui.recycler.LinkListAdapter;

public class MainActivity extends ReednitActivity implements LinkListAdapter.OnLinkClickListener {

    private boolean mIsDualPane;
    private LinkDisplayFragment mLinkDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment =
                (MainFragment) fragmentManager.findFragmentById(R.id.fl_fragment_primary);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_primary, mainFragment)
                    .commit();
        }

        mLinkDisplayFragment =
                (LinkDisplayFragment) fragmentManager.findFragmentById(R.id.fl_fragment_secondary);
        View secondaryFragmentView = findViewById(R.id.fl_fragment_secondary);
        mIsDualPane = secondaryFragmentView != null && secondaryFragmentView.getVisibility() == View.VISIBLE;

        if (mIsDualPane && mLinkDisplayFragment == null) {
            mLinkDisplayFragment = new LinkDisplayFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_secondary, mLinkDisplayFragment)
                    .commit();
        }else if(mLinkDisplayFragment != null){
            fragmentManager.beginTransaction()
                    .remove(mLinkDisplayFragment)
                    .commit();
        }

    }

    @Override
    public void onLinkClick(Link link) {
        if(mIsDualPane){
            mLinkDisplayFragment.setLink(link);
            return;
        }
        Intent intent = new Intent(this, LinkDisplayActivity.class);
        intent.putExtra(Link.EXTRA_LINK, link);
        startActivity(intent);
    }
}