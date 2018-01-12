package com.finch.hothead.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finch.hothead.G;
import com.finch.hothead.db.tables.Banner;
import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.db.tables.Review;
import com.finch.hothead.db.tables.Sauce;
import com.finch.hothead.db.tables.User;
import com.finch.hothead.utils.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by finchrat on 7/31/2016.
 */
public class DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "HotheadDB";
    private final AssetManager assetManager;

    public DB(Context context, AssetManager assetManager) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.assetManager = assetManager;
//        load();
    }

    private void load() {
        JSONObject jsonObject = null;
        try {
            String databaseFile = "database.json";
            jsonObject = IOUtils.getJsonFromAsset(assetManager, databaseFile);
        } catch (IOException e) {
            //TODO something
        }

        if (jsonObject == null) {
            //TODO something
        }

        //Load sauces
        if (jsonObject.has("sauces")) {
            try {
                Sauce.load(getWritableDatabase(), jsonObject.getJSONArray("sauces"));
            } catch (JSONException e) {
                //TODO something
            }
        }

        //Load banners
        if (jsonObject.has("banners")) {
            try {
                Banner.load(getWritableDatabase(), jsonObject.getJSONArray("banners"));
            } catch (JSONException e) {
                //TODO something
            }
        }

        //Load reviews
//        if (jsonObject.has("reviews")) {
//            try {
//                Review.load(getWritableDatabase(), jsonObject.getJSONArray("reviews"));
//            } catch (JSONException e) {
//                //TODO something
//            }
//        }
    }

    private void checkTables() {
        final ArrayList<String> dirArray = new ArrayList<>();

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        while (c.moveToNext()) {
            String s = c.getString(0);
            if (s.equals("android_metadata")) {
                continue;
            } else {
                dirArray.add(s);
            }
        }
        c.close();
        //dirArray
    }

    private void checkData() {
        final ArrayList<String> dirArray = new ArrayList<>();

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor c = DB.rawQuery(
                "SELECT * FROM sauce"
                , null);
        while (c.moveToNext()) {
            String s = c.getString(0);
            if (s.equals("android_metadata")) {
                continue;
            } else {
                dirArray.add(s);
            }
        }
        c.close();
        //dirArray
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Banner.CREATE_TABLE);
        db.execSQL(Review.CREATE_TABLE);
        db.execSQL(Sauce.CREATE_TABLE);
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Bookmark.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Sauce.DROP_TABLE);
        db.execSQL(Review.DROP_TABLE);
        db.execSQL(Banner.DROP_TABLE);
        db.execSQL(User.DROP_TABLE);
        db.execSQL(Bookmark.DROP_TABLE);
        onCreate(db);
    }

    //Static methods*******************************************
    public static String contains(String input) {
        if (input == null || input.length() == 0)
            return "%";
        return "%" + input + "%";
    }

    public static List<Map<String, String>> getResultList(Context context, String file) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(
                        context.getResources().getIdentifier(file, "raw", context.getPackageName())
                )));
        StringBuilder query = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                query.append(line).append('\n');
            }
        } catch (IOException e) {
            // TODO tell us what happened
            return null;
        }

        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(query.toString(), null);
        List<Map<String, String>> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                //todo add each column as map entry, then add map to list

            } while (cursor.moveToNext());
        }
        return list;
    }
}

