package com.reednit.android.ui.recycler;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.reednit.android.R;
import com.reednit.android.room.Link;
import com.reednit.android.ui.util.GlideApp;
import com.reednit.android.ui.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;


public class LinkListAdapter extends RecyclerView.Adapter<LinkListAdapter.LinkViewHolder>{

    private final List<Link> mLinkList;
    private final OnLinkClickListener mOnLinkClickListener;

    public LinkListAdapter(OnLinkClickListener onLinkClickListener){
        mLinkList = new ArrayList<>();
        mOnLinkClickListener = onLinkClickListener;
    }

    public interface OnLinkClickListener {
        void onLinkClick(Link link);
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.link, parent, false);
        return new LinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public void onViewRecycled(LinkViewHolder holder) {
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return mLinkList.size();
    }

    public void update(List<Link> newLinkList){
        if(newLinkList == null) return;
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new LinkListDiffCallback(newLinkList, mLinkList), true);
        mLinkList.clear();
        mLinkList.addAll(newLinkList);
        diffResult.dispatchUpdatesTo(this);
    }

    class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView titleView;
        final SquareImageView thumbnailView;

        LinkViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.tv_title);
            titleView.setOnClickListener(this);
            thumbnailView = itemView.findViewById(R.id.siv_thumbnail);
            thumbnailView.setOnClickListener(this);
        }

        void bind(int position){
            Link link = mLinkList.get(position);
            titleView.setText(link.title);

            if(!URLUtil.isValidUrl(link.thumbnail)) return;

            Context context = this.thumbnailView.getContext();
            GlideApp.with(context)
                    .load(link.thumbnail)
                    .fitCenter()
                    .into(thumbnailView);
        }

        void recycle(){
            thumbnailView.setImageDrawable(null);
        }

        @Override
        public void onClick(View view) {
            if(view == thumbnailView) {
                Link link = mLinkList.get(getAdapterPosition());
                mOnLinkClickListener.onLinkClick(link);
            }
        }
    }

}
