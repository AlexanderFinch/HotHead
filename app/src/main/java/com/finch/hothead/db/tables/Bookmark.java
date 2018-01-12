package com.finch.hothead.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finch.hothead.G;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by finchrat on 7/31/2016.
 */
public class Bookmark {
    private String key;
    private String sauceKey;
    private String userKey;


    private static final String TABLE = "bookmark";

    public enum COL {
        BOOKMARK_KEY("BOOKMARK_KEY", "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"),
        SAUCE_KEY("SAUCE_KEY", "INTEGER"),
        USER_KEY("USER_KEY", "INTEGER");
        private String name;
        private String type;
        COL(String name, String type) {
            this.name = name;
            this.type = type;
        }
        public String getType() {
            return this.type;
        }
        public String toString() {
            return this.name;
        }
    }

    //Queries **************************************************************
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE + "(" +
            COL.BOOKMARK_KEY + " " + COL.BOOKMARK_KEY.getType() + "," +
            COL.SAUCE_KEY + " " + COL.SAUCE_KEY.getType() + "," +
            COL.USER_KEY + " " + COL.USER_KEY.getType() + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String columnNameInsert = COL.SAUCE_KEY + ", " +
            COL.USER_KEY;
    private static final String columnNameList = COL.BOOKMARK_KEY + ", " + columnNameInsert;
    private static final java.lang.String INSERT = "INSERT INTO " + TABLE + " (" + columnNameInsert + ") VALUES (?,?);";
    private static final String SELECT_STAR = "SELECT * FROM " + TABLE;
    private static final String SELECT_USER_BOOKMARKS = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE " + COL.USER_KEY + " = ?";
    private static final String SELECT_USER_BOOKMARK = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE " + COL.USER_KEY + " = ?" +
            " AND " + COL.SAUCE_KEY + " = ?";

    public Bookmark() {
    }

    public Bookmark(Cursor cursor) {
        key = String.valueOf(cursor.getInt(0));
        sauceKey = cursor.getString(1);
        userKey = cursor.getString(2);
    }

    public Bookmark(String sauceKey, String userKey) {
        this.sauceKey = sauceKey;
        this.userKey = userKey;
    }

    public int save() {
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_USER_BOOKMARK, new String[]{this.getUserKey(), this.getSauceKey()});
        if (cursor.moveToFirst()) {
            // this means there was a match, don't update
            return 0;
        }

        G.db.getWritableDatabase().execSQL(INSERT, new String[]{getSauceKey(), getUserKey()});
        return 1;
    }

    public static List<Bookmark> getUserBookmarks() {
        List<Bookmark> sauces = new ArrayList<>();
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_USER_BOOKMARKS, new String[]{G.user.getKey()});
        if (cursor.moveToFirst()) {
            do {
                Bookmark sauce = new Bookmark(cursor);
                sauces.add(sauce);
            } while (cursor.moveToNext());
        }
        return sauces;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getSauceKey() {
        return sauceKey;
    }

    public void setSauceKey(String sauceKey) {
        this.sauceKey = sauceKey;
    }
}
