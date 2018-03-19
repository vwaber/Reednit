package com.reednit.android.persistence;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LocalContentProvider extends ContentProvider{

    private static final int CODE_LISTINGS = 100;
    private static final int CODE_LINKS = 200;

    private LocalDbHelper mDbHelper;
    private ContentResolver mContentResolver;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = LocalContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, LocalContract.PATH_LISTINGS, CODE_LISTINGS);
        matcher.addURI(authority, LocalContract.PATH_LINKS, CODE_LINKS);
        return matcher;
    }

    private static String getDBTableName(Uri uri){
        String dBTableName;
        switch (sUriMatcher.match(uri)) {
            case CODE_LINKS:
                dBTableName = LocalContract.LinkEntry.TABLE_NAME;
                break;
            default:
                dBTableName = null;
        }
        return dBTableName;
    }

    @Override
    public boolean onCreate() {
        if(getContext() == null) return false;
        mDbHelper = new LocalDbHelper(getContext());
        mContentResolver = getContext().getContentResolver();
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor result;
        String tableName = getDBTableName(uri);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        result = db.query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        //Allows CursorLoader to retrigger onLoadFinished on ContentProvider data change
        if(mContentResolver != null) {
            result.setNotificationUri(mContentResolver, uri);
        }

        return result;

    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String tableName = getDBTableName(uri);
        Uri insertedItemUri;
        long _id;

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        _id = db.insert(tableName, null, values);
        insertedItemUri = ContentUris.withAppendedId(uri, _id);

        return insertedItemUri;

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values){
        int rowCount = super.bulkInsert(uri, values);
        //Allows CursorLoader to retrigger onLoadFinished on ContentProvider data change
        if(mContentResolver != null) {
            mContentResolver.notifyChange(uri, null);
        }
        return rowCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        String tableName = getDBTableName(uri);

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int count = db.delete(tableName, selection, selectionArgs);
        db.close();

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
