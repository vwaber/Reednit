package com.reednit.android.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

public final class LocalContract {

    static final String CONTENT_AUTHORITY = "com.reednit.android";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_LISTINGS = "listings";
    static final String PATH_LINKS = "links";
    static final String PATH_COMMENTS = "comments";

    private LocalContract() {}

    private interface Thing {
        String COLUMN_NAME_NAME = "name";
    }

    private interface Votable {
        String COLUMN_NAME_UPVOTES =    "upvotes";
        String COLUMN_NAME_DOWNVOTES =  "downvotes";
    }

    private interface Created {
        String COLUMN_NAME_CREATED_UTC =    "created_utc";
        String COLUMN_NAME_CREATED_LOCAL =  "created_local";
    }

    public static class LinkEntry implements BaseColumns, Thing, Votable, Created {
        static final String TABLE_NAME =                        "links";
        public static final String COLUMN_NAME_TITLE =          "title";
        public static final String COLUMN_NAME_URL =            "url";
        public static final String COLUMN_NAME_THUMBNAIL =      "thumbnail";
        public static final String COLUMN_NAME_LIKES =          "like_count";
        public static final String COLUMN_NAME_IS_SELF =        "is_self";
        public static final String COLUMN_NAME_IS_VIDEO =       "is_video";
        public static final String COLUMN_NAME_SELFTEXT =       "selftext";
        public static final String COLUMN_NAME_SELFTEXT_HTML =  "selftext_html";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LINKS)
                .build();
    }

    static class CommentEntry implements BaseColumns, Thing, Votable, Created {
        static final String TABLE_NAME =             "comments";
        static final String COLUMN_NAME_AUTHOR =     "author";
        static final String COLUMN_NAME_BODY =       "body";
        static final String COLUMN_NAME_BODY_HTML =  "body_html";

        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_COMMENTS)
                .build();
    }

}
