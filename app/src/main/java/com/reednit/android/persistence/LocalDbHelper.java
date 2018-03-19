package com.reednit.android.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.reednit.android.persistence.LocalContract.LinkEntry;

class LocalDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "reednit.db";

    private static final String SQL_CREATE_LINKS =
            "CREATE TABLE " + LinkEntry.TABLE_NAME + " (" +
                    LinkEntry._ID +                         " INTEGER PRIMARY KEY, " +
                    LinkEntry.COLUMN_NAME_NAME +            " TEXT, " +
                    LinkEntry.COLUMN_NAME_TITLE +           " TEXT, " +
                    LinkEntry.COLUMN_NAME_URL +             " TEXT, " +
                    LinkEntry.COLUMN_NAME_THUMBNAIL +       " TEXT, " +
                    LinkEntry.COLUMN_NAME_CREATED_UTC +     " REAL, " +
                    LinkEntry.COLUMN_NAME_IS_SELF +         " INTEGER, " +
                    LinkEntry.COLUMN_NAME_IS_VIDEO +        " INTEGER, " +
                    LinkEntry.COLUMN_NAME_SELFTEXT +        " TEXT, " +
                    LinkEntry.COLUMN_NAME_SELFTEXT_HTML +   " TEXT" +
                    ");";

    private static final String SQL_DELETE_LINKS =
            "DROP TABLE IF EXISTS " + LinkEntry.TABLE_NAME;

    LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LocalDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LINKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_LINKS);
        onCreate(db);
    }
}
