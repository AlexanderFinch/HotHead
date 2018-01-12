package com.finch.hothead.db.tables;

import android.database.Cursor;

import com.finch.hothead.G;

import java.util.UUID;

/**
 * Created by finchrat on 7/9/2016.
 */
public class User {
    private String key;
    private String screenName;
    private String firstName;
    private String lastName;
    private String email;
    private String code;
    private String hash;

    private static final String TABLE = "users";

    public boolean isAppRegistered() {
        return hash != null;
    }

    public enum COL {
        USER_KEY("USER_KEY", "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"),
        SCREEN_NAME("SCREEN_NAME", "TEXT UNIQUE"),
        FIRST_NAME("FIRST_NAME", "TEXT"),
        LAST_NAME("LAST_NAME", "TEXT"),
        EMAIL("EMAIL", "TEXT");

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
            COL.USER_KEY + " " + COL.USER_KEY.getType() + "," +
            COL.SCREEN_NAME + " " + COL.SCREEN_NAME.getType() + "," +
            COL.FIRST_NAME + " " + COL.FIRST_NAME.getType() + "," +
            COL.LAST_NAME + " " + COL.LAST_NAME.getType() + "," +
            COL.EMAIL + " " + COL.EMAIL.getType() + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String columnNameInsert = COL.SCREEN_NAME + ", " +
            COL.FIRST_NAME + ", " +
            COL.LAST_NAME + ", " +
            COL.EMAIL;
    private static final String columnNameList = COL.USER_KEY + ", " + columnNameInsert;

    private static final String INSERT = "INSERT INTO " + TABLE + " (" + columnNameInsert + ") VALUES (?,?,?,?);";
    private static final String DELETE = "DELETE FROM " + TABLE;
    private static final String DELETE_ROW = "DELETE FROM " + TABLE + " WHERE " + COL.USER_KEY + " = ?";
    private static final String SELECT_USER_BY_SCREEN_NAME = "SELECT " + COL.USER_KEY.toString() + " FROM " + TABLE + " WHERE " + COL.SCREEN_NAME.toString() + " = ?";
    private static final String SELECT_USER_BY_KEY = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE " + COL.USER_KEY.toString() + " = ";

    public User() {
    }

    public User(String screenName, String firstName, String lastName, String email) {
        this.screenName = screenName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String key, String screenName, String firstName, String lastName, String email, String code, String hash) {
        this.key = key;
        this.screenName = screenName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.code = code;
        this.hash = hash;
    }

    public User(Cursor cursor) {
        this.key = cursor.getString(0);
        this.screenName = cursor.getString(1);
        this.firstName = cursor.getString(2);
        this.lastName = cursor.getString(3);
        this.email = cursor.getString(4);
    }

    public String save() {
        G.db.getWritableDatabase().execSQL(INSERT, new String[]{getScreenName(), getFirstName(),
                getLastName(), getEmail()});
        Cursor cursor = G.db.getReadableDatabase().rawQuery(SELECT_USER_BY_SCREEN_NAME, new String[]{getScreenName()});
        String id = null;
        if (cursor.moveToFirst()) {
            id = cursor.getString(0);
        }
        return id;
    }

    public void delete() {
        if (this.key != null) {
            G.db.getWritableDatabase().rawQuery(DELETE_ROW, new String[]{this.key});
        }
        deleteAll();
    }

    public void deleteAll() {
        G.db.getWritableDatabase().execSQL(DELETE);
    }


    public boolean isEmpty() {
        if (getScreenName() == null ||
                getScreenName().isEmpty() ||
                getEmail() == null ||
                getEmail().isEmpty())
            return true;
        return false;
    }

    public static User getUserByKey(String key) {
        User user = new User();
        Cursor cursor = G.db.getReadableDatabase().rawQuery(SELECT_USER_BY_KEY + key, null);
        if (cursor.moveToFirst()) {
            user = new User(cursor);
        }
        return user;
    }

    public void createHash() {
        hash = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public String getHash() {
        return hash;
    }

}
