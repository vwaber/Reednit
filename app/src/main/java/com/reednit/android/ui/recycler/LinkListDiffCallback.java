package com.reednit.android.ui.recycler;

import android.support.v7.util.DiffUtil;

import com.reednit.android.repository.local.Link;

import java.util.List;
import java.util.Objects;

class LinkListDiffCallback extends DiffUtil.Callback{

    private final List<Link> mOldLinkList;
    private final List<Link> mNewLinkList;

    LinkListDiffCallback(List<Link> newLinkList, List<Link> oldLinkList){
        mNewLinkList = newLinkList;
        mOldLinkList = oldLinkList;
    }

    @Override
    public int getOldListSize() {
        return mOldLinkList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewLinkList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        String newName = mNewLinkList.get(newItemPosition).getName();
        String oldName = mOldLinkList.get(oldItemPosition).getName();
        return (newName == null ? oldName == null : newName.equals(oldName));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Link newItem = mNewLinkList.get(newItemPosition);
        Link oldItem = mOldLinkList.get(oldItemPosition);
        return Objects.equals(newItem, oldItem);
    }

}
