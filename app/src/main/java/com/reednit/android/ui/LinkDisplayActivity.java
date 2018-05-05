package com.reednit.android.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.reednit.android.R;
import com.reednit.android.ui.activity.ReednitActivity;
import com.reednit.android.valueobject.LinkKt;

public class LinkDisplayActivity extends ReednitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        LinkDisplayFragment mLinkDisplayFragment = (LinkDisplayFragment) fragmentManager.findFragmentById(R.id.fl_fragment);

        if(mLinkDisplayFragment == null) {
            mLinkDisplayFragment = new LinkDisplayFragment();
            if (getIntent().hasExtra(LinkKt.EXTRA_LINK_UID)) {
                mLinkDisplayFragment.setArguments(getIntent().getExtras());
            }
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment, mLinkDisplayFragment)
                    .commit();
        }

    }

}
