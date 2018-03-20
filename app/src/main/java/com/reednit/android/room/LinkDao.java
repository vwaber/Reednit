package com.reednit.android.room;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

public interface LinkDao {

    @Query("SELECT * FROM link")
    List<Link> getAll();

    @Query("DELETE FROM link")
    void deleteAll();

    @Insert
    void insertAll(Link... links);

}
