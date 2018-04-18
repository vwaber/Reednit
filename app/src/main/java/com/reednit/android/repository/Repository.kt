package com.reednit.android.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.reednit.android.repository.local.AppDatabase
import com.reednit.android.repository.local.Link
import com.reednit.android.repository.local.LinkDao
import com.reednit.android.repository.remote.NetworkClient

class Repository(context: Context) {

    private val mNetworkClient: NetworkClient = NetworkClient.getInstance()
    private val mLinkDao: LinkDao = AppDatabase.getDatabase(context).linkDao()

    fun getAllLinks(): LiveData<List<Link>> {
        return mLinkDao.all
    }

    fun getLink(uid: Int): LiveData<Link> {
        return mLinkDao.get(uid)
    }

    fun fetchFreshLinks(): Boolean {
        val links: List<Link> = mNetworkClient.fetchLinks(null)
        if(links.isEmpty()) return false
        mLinkDao.deleteAll()
        mLinkDao.insert(links)
        return true
    }

    fun fetchAdditionalLinks(): Boolean {
        val links: List<Link> = mNetworkClient.fetchLinks(mLinkDao.last.name)
        if(links.isEmpty()) return false
        mLinkDao.insert(links)
        return true
    }

}