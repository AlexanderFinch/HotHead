package com.finch.hothead.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finch.hothead.G;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by finchrat on 7/31/2016.
 */
public class Banner {
    private String key;
    private String message;

    private static final String TABLE = "banner";

    public enum COL {
        SAUCE_KEY("SAUCE_KEY", "INTEGER PRIMARY KEY"),
        MESSAGE("MESSAGE", "TEXT");

        private String name;
        private String type;

        COL(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public String toString() { return name; }
    }

    //Queries **************************************************************
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE + "(" +
            COL.SAUCE_KEY + " " + COL.SAUCE_KEY.getType() + "," +
            COL.MESSAGE + " " + COL.MESSAGE.getType() + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String columnNameList = COL.SAUCE_KEY + ", " +
            COL.MESSAGE;
    private static final String SELECT_STAR = "SELECT * FROM " + TABLE;

    public Banner() {
    }

    public Banner(String message) {
        this.message = message;
    }

    public static void load(SQLiteDatabase db, JSONArray banners) {
        if (banners == null)
            return;
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < banners.length(); i++) {
                JSONObject banner = banners.getJSONObject(i);
                values.put(COL.SAUCE_KEY.toString(), banner.getString(COL.SAUCE_KEY.toString()));
                values.put(COL.MESSAGE.toString(), banner.getString(COL.MESSAGE.toString()));
                long row = db.insert(TABLE, null, values);
                if(row == -1){
                    throw new Exception("db.insert received a -1");
                }
            }
            db.close();
        } catch (Exception  e) {
            //TODO Something
            System.out.print(e);
        }
    }

    //http://stackoverflow.com/questions/27498763/create-a-separate-database-class-android
    public static List<Banner> getBanners() {
        List<Banner> banners = new ArrayList<>();
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_STAR,null);//
        if (cursor.moveToFirst()) {
            do {
                Banner banner = new Banner();
                banner.setKey(String.valueOf(cursor.getInt(0)));
                banner.setMessage(cursor.getString(1));
                banners.add(banner);
            } while (cursor.moveToNext());
        }
        return banners;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
