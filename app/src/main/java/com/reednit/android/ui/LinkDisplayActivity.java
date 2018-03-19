package com.reednit.android.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.reednit.android.R;
import com.reednit.android.model.Link;
import com.reednit.android.ui.activity.ReednitActivity;

public class LinkDisplayActivity extends ReednitActivity
        implements LinkDisplayFragment.OnFragmentCreatedListener {

    private LinkDisplayFragment mLinkDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mLinkDisplayFragment =
                (LinkDisplayFragment) fragmentManager.findFragmentById(R.id.fl_fragment);

        if(mLinkDisplayFragment == null){
            mLinkDisplayFragment = new LinkDisplayFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment, mLinkDisplayFragment)
                    .commit();
        }

    }

    @Override
    public void onFragmentCreated() {
        Link link = getIntent().getParcelableExtra(Link.EXTRA_LINK);
        mLinkDisplayFragment.setLink(link);
    }
}
