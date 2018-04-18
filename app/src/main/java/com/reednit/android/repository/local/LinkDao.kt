package com.reednit.android.repository.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface LinkDao {

    @Query("SELECT * FROM link")
    fun loadAll(): LiveData<List<Link>>

    @Query("SELECT * FROM link WHERE uid = :uid")
    fun load(uid: Int): LiveData<Link>

    @Query("SELECT * FROM link ORDER BY uid DESC LIMIT 1")
    fun findLast(): Link

    @Query("DELETE FROM link")
    fun deleteAll()

    @Insert
    fun insert(links: List<Link>)

}