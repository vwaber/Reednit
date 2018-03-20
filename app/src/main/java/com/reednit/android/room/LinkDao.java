package com.reednit.android.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LinkDao {

    @Query("SELECT * FROM link")
    LiveData<List<Link>> getAll();

    @Query("SELECT * FROM link ORDER BY uid DESC LIMIT 1")
    Link getLast();

    @Query("DELETE FROM link")
    void deleteAll();

    @Insert
    void insert(Link link);

    @Insert
    void insert(Link... links);

}
