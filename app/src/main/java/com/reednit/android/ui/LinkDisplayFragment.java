package com.reednit.android.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.reednit.android.R;
import com.reednit.android.viewmodel.LinkViewModel;
import com.reednit.android.repository.local.Link;
import com.reednit.android.ui.util.GlideApp;

public class LinkDisplayFragment extends Fragment{

    private static final String MIME_IMAGE_PREFIX = "image/";
    private static final String ABOUT_BLANK = "about:blank";

    private ImageView mDisplayImageView;
    private WebView mDisplayWebView;
    private TextView mDisplayTextView;

    private Link mLink;

    public LinkDisplayFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getActivity() == null) return;

        LinkViewModel linkViewModel = ViewModelProviders.of(getActivity()).get(LinkViewModel.class);

        if(getArguments() != null && getArguments().containsKey(Link.EXTRA_LINK_UID)){
            int uid = getArguments().getInt(Link.EXTRA_LINK_UID);
            linkViewModel.get(uid).observe(this, new Observer<Link>() {
                @Override
                public void onChanged(@Nullable Link link) {
                    setLink(link);
                }
            });
        }else{
            linkViewModel.getSelected().observe(this, new Observer<Link>() {
                @Override
                public void onChanged(@Nullable Link link) {
                    setLink(link);
                }
            });
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_link_display, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                setLink(mLink);
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_link_display, container, false);
        mDisplayImageView = rootView.findViewById(R.id.iv_display);
        mDisplayWebView = rootView.findViewById(R.id.wv_display);
        mDisplayTextView = rootView.findViewById(R.id.tv_display);

        AdView adView = rootView.findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return rootView;
    }

    private void setLink(Link link) {

        if(link == null) return;

        mLink = link;

        if(getActivity() != null)
            getActivity().setTitle(link.title);

        mDisplayWebView.loadUrl(ABOUT_BLANK);

        mDisplayImageView.setVisibility(View.GONE);
        mDisplayWebView.setVisibility(View.GONE);
        mDisplayTextView.setVisibility(View.GONE);

        if(link.isSelf){
            displaySelftext(link.selftext);
            return;
        }

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension = MimeTypeMap.getFileExtensionFromUrl(link.url);
        String mimeType = mimeTypeMap.getMimeTypeFromExtension(extension);

        if (mimeType != null && mimeType.startsWith(MIME_IMAGE_PREFIX)) {
            displayImage(link.url);
            return;
        }

        displayWeb(link.url);

    }

    private void displayImage(String url){
        if(getActivity() == null) return;
        mDisplayImageView.setVisibility(View.VISIBLE);

        GlideApp.with(getActivity())
                .load(url)
                .fitCenter()
                .into(mDisplayImageView);
    }

    private void displayWeb(String url){
        mDisplayWebView.setVisibility(View.VISIBLE);
        mDisplayWebView.loadUrl(url);
    }

    private void displaySelftext(String text){
        mDisplayTextView.setVisibility(View.VISIBLE);
        mDisplayTextView.setText(text);
    }

}
