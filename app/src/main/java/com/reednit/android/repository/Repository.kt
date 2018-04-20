package com.reednit.android.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.reednit.android.repository.local.AppDatabase
import com.reednit.android.repository.local.Link
import com.reednit.android.repository.local.LinkDao
import com.reednit.android.repository.remote.NetworkClient

class Repository(context: Context) {

    private val mNetworkClient: NetworkClient = NetworkClient
    private val mLinkDao: LinkDao = AppDatabase.getDatabase(context).linkDao()

    fun getAllLinks(): LiveData<List<Link>> {
        return mLinkDao.loadAll()
    }

    fun getLink(uid: Int): LiveData<Link> {
        return mLinkDao.load(uid)
    }

    fun fetchFreshLinks(): Boolean {
        val links: List<Link> = mNetworkClient.fetchLinks("")
        if(links.isEmpty()) return false
        mLinkDao.deleteAll()
        mLinkDao.insert(links)
        return true
    }

    fun fetchAdditionalLinks(): Boolean {
        val links: List<Link> = mNetworkClient.fetchLinks(mLinkDao.findLast().name)
        if(links.isEmpty()) return false
        mLinkDao.insert(links)
        return true
    }

}