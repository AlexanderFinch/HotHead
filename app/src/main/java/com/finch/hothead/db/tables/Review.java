package com.finch.hothead.db.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finch.hothead.G;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by finchrat on 7/17/2016.
 */
public class Review {
    private String key;
    private String sauceKey;
    private String userKey;
    private String comments;
    private float rating;
    private long dateReviewed;

    private static final String TABLE = "review";

    public enum COL {
        REVIEW_KEY("REVIEW_KEY", "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"),
        SAUCE_KEY("SAUCE_KEY", "INTEGER"),
        USER_KEY("USER_KEY", "INTEGER"),
        COMMENTS("COMMENTS", "TEXT"),
        DATE_REVIEWED("DATE_REVIEWED", "INTEGER"),
        RATING("RATING", "NUMBER");

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
            COL.REVIEW_KEY + " " + COL.REVIEW_KEY.getType() + "," +
            COL.SAUCE_KEY + " " + COL.SAUCE_KEY.getType() + "," +
            COL.USER_KEY + " " + COL.USER_KEY.getType() + "," +
            COL.COMMENTS + " " + COL.COMMENTS.getType() + "," +
            COL.RATING + " " + COL.RATING.getType() + "," +
            COL.DATE_REVIEWED + " " + COL.DATE_REVIEWED.getType() + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String columnNameInsert = COL.SAUCE_KEY + ", " +
            COL.USER_KEY + ", " +
            COL.COMMENTS + ", " +
            COL.RATING + ", " +
            COL.DATE_REVIEWED;
    private static final String columnNameList = COL.REVIEW_KEY + ", " + columnNameInsert;

    private static final String INSERT = "INSERT INTO " + TABLE + " (" + columnNameInsert + ") VALUES (?,?,?,?,?);";
    private static final String DELETE = "DELETE FROM " + TABLE;
    private static final String DELETE_ROW = "DELETE FROM " + TABLE + " WHERE " + COL.REVIEW_KEY + " = ?";
    private static final String SELECT_RECENT_REVIEWS = "SELECT " + columnNameList + " FROM " +
            TABLE + " ORDER BY " + COL.DATE_REVIEWED + " DESC LIMIT 7";
    private static final String SELECT_RECENT_REVIEWS_BY_KEY = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE " + COL.SAUCE_KEY + " = ? " +
            " ORDER BY " + COL.DATE_REVIEWED +
            " DESC LIMIT 5";
    private static final String SELECT_REVIEWS_BY_USER = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE " + COL.USER_KEY + " = ?";

    public Review(String sauceKey, String userKey, String comments, float rating) {
        this.sauceKey = sauceKey;
        this.userKey = userKey;
        this.comments = comments;
        this.rating = rating;
        this.dateReviewed = Calendar.getInstance().getTimeInMillis();
    }

    public Review(String key, String sauceKey, String userKey, String comments, float rating) {
        this.key = key;
        this.sauceKey = sauceKey;
        this.userKey = userKey;
        this.comments = comments;
        this.rating = rating;
        this.dateReviewed = Calendar.getInstance().getTimeInMillis();
    }

    public Review(Cursor cursor) {
        this.key = cursor.getString(0);
        this.sauceKey = cursor.getString(1);
        this.userKey = cursor.getString(2);
        this.comments = cursor.getString(3);
        this.rating = cursor.getFloat(4);
        this.dateReviewed = cursor.getInt(5);
    }

    public void save() {
        G.db.getWritableDatabase().execSQL(INSERT, new String[]{getSauceKey(), getUserKey(),
                getComments(), Float.toString(getRating()),
                String.valueOf(Calendar.getInstance().getTimeInMillis())});
    }

    public void deleteReview(Review review) {
        G.db.getWritableDatabase().execSQL(DELETE_ROW, new String[]{review.getKey()});
    }

//    public static List<Map<String, String>> getPopularReviews(Context context) {
//        //SELECT_RECENT_REVIEWS
//        return DB.getResultList(context, "popular", Review.class);
//    }

    public static List<Review> getRecentReviews(String key) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_RECENT_REVIEWS_BY_KEY, new String[]{key});//
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review(cursor);
                reviews.add(review);
            } while (cursor.moveToNext());
        }
        return reviews;
    }

    public static List<Review> getUserReviews() {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_REVIEWS_BY_USER, new String[]{G.user.getKey()});//
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review(cursor);
                reviews.add(review);
            } while (cursor.moveToNext());
        }
        return reviews;
    }

    public static void deleteAllReviews() {
        G.db.getWritableDatabase().execSQL(DELETE);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDateReviewed() {

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        Date date;
        try {
            date = iso8601Format.parse(String.valueOf(dateReviewed));
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

//    public void setDateReviewed(Date dateReviewed) {
//        this.dateReviewed = dateReviewed;
//    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
