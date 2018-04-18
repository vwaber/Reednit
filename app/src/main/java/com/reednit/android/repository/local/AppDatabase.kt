package com.reednit.android.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

private const val DATABASE_NAME: String = "reednit.db"

@Database(entities = [Link::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun linkDao(): LinkDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

    }

}