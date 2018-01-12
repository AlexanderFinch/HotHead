package com.finch.hothead.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finch.hothead.G;
import com.finch.hothead.db.DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by finchrat on 7/31/2016.
 */
public class Sauce {
    private String key;
    private String name;
    private String description;
    private String companyKey;
    private String shu;
    private String hotness;
    private String pepper;
    private String image;
    private Map<String, String> extra = new HashMap<>();
    public static final String RATING_COUNT = "RATING_COUNT";

    public enum Hotness {
        MILD("mild","0-1,000"),
        MEDIUM("medium","1,001-10,000"),
        HOT("hot","10,001-25,000"),
        XXXHOT("xxxhot","25,001-150,000"),
        INSANE("insane","150,001-750,000");

        private String value;

        public String getShu() {
            return shu;
        }

        private String shu;

        Hotness(String value, String shu){
            this.value = value;
            this.shu = shu;
        }

        public String toString(){
            return value;
        }

        public static String[] getShuScale() {
            Hotness[] values = Hotness.values();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                list.add(values[i].getShu());
            }
            return list.toArray(new String[list.size()]);
        }

        public static String[] getHotnessScale() {
            Hotness[] values = Hotness.values();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                list.add(values[i].toString());
            }
            Collections.sort(list);
            return list.toArray(new String[list.size()]);
        }
    }

    private static final String TABLE = "sauce";

    public enum COL {
        SAUCE_KEY("SAUCE_KEY", "INTEGER PRIMARY KEY"),
        NAME("NAME", "TEXT"),
        DESCRIPTION("DESCRIPTION", "TEXT"),
        COMPANY_KEY("COMPANY_KEY", "INTEGER"),
        SHU("SHU", "INTEGER"),
        HOTNESS("HOTNESS", "TEXT"),
        PEPPER("PEPPER", "TEXT"),
        IMAGE("IMAGE", "TEXT");

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
            COL.SAUCE_KEY + " " + COL.SAUCE_KEY.getType() + "," +
            COL.NAME + " " + COL.NAME.getType() + "," +
            COL.DESCRIPTION + " " + COL.DESCRIPTION.getType() + "," +
            COL.COMPANY_KEY + " " + COL.COMPANY_KEY.getType() + "," +
            COL.SHU + " " + COL.SHU.getType() + "," +
            COL.HOTNESS + " " + COL.HOTNESS.getType() + "," +
            COL.PEPPER + " " + COL.PEPPER.getType() + "," +
            COL.IMAGE + " " + COL.IMAGE.getType() + ")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
    private static final String columnNameInsert = COL.NAME + ", " +
                    COL.DESCRIPTION + ", " +
                    COL.COMPANY_KEY + ", " +
                    COL.SHU + ", " +
                    COL.HOTNESS + ", " +
                    COL.PEPPER + ", " +
                    COL.IMAGE;
    private static final String columnNameList = COL.SAUCE_KEY + ", " + columnNameInsert;
    private static final String SELECT_SAUCE_BY_KEYWORD = "SELECT " + columnNameList + ", " + Review.COL.RATING +
            " FROM " + TABLE +
            " WHERE " + COL.NAME + " LIKE ? OR " +
            COL.DESCRIPTION + " LIKE ? OR " +
            COL.PEPPER + " LIKE ? OR " +
            COL.HOTNESS + " LIKE ?";
    private static final String SELECT_SAUCE_BY_KEYWORD_AVG_RATING = "SELECT " + columnNameList +
            ", (select avg(rating) from review where review.sauce_key = sauce.sauce_key) as " + Review.COL.RATING +
            ", (select count(*) from review where review.sauce_key = sauce.sauce_key) as " + RATING_COUNT +
            " FROM " + TABLE +
            " WHERE " + COL.NAME + " LIKE ? OR " +
            COL.DESCRIPTION + " LIKE ? OR " +
            COL.PEPPER + " LIKE ? OR " +
            COL.HOTNESS + " LIKE ?";
    private static final String SELECT_SAUCE_BY_KEYWORD_ORDERED = "SELECT 1 as rank, " + columnNameList +
            " FROM " + TABLE + " WHERE " + COL.NAME + " LIKE ? " +
            "UNION SELECT 2 as rank, " + columnNameList +
            " FROM " + TABLE + " WHERE " + COL.DESCRIPTION + " LIKE ? " +
            "UNION SELECT 3 as rank, " + columnNameList +
            " FROM " + TABLE + " WHERE " + COL.PEPPER + " LIKE ? " +
            "UNION SELECT 4 as rank, " + columnNameList +
            " FROM " + TABLE + " WHERE " + COL.HOTNESS + " LIKE ? " +
            "ORDER BY rank;";
    private static final String SELECT_STAR = "SELECT * FROM " + TABLE;
    private static final String SELECT_SAUCE_BY_NAME = "SELECT * FROM " + TABLE + " WHERE " + COL.NAME + " LIKE ?";
    private static final String SELECT_SAUCE_BY_KEY = "SELECT * FROM " + TABLE + " WHERE " + COL.SAUCE_KEY + " = ";
    private static final String SELECT_SAUCE_BY_KEY_AVG_RATING = "SELECT " + columnNameList +
            ", (select avg(rating) from review where review.sauce_key = sauce.sauce_key) as " + Review.COL.RATING +
            ", (select count(*) from review where review.sauce_key = sauce.sauce_key) as " + RATING_COUNT +
            " FROM " + TABLE +
            " WHERE " + COL.SAUCE_KEY + " = ";
    private static final String SELECT_RANDOM_SAUCE = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE (? IS 'all' OR SAUCE.HOTNESS in (?))" +
            " ORDER BY RANDOM() LIMIT 1;";
    private static final String SELECT_RANDOM_SAUCE2 = "SELECT " + columnNameList +
            " FROM " + TABLE +
            " WHERE SAUCE.HOTNESS in (?,?)" +
            " ORDER BY RANDOM() LIMIT 1;";
    private static final String INSERT = "INSERT INTO " + TABLE + " (" + columnNameInsert + ") VALUES (?,?,?,?,?,?,?);";


    public Sauce() {
    }

    public Sauce(Cursor cursor) {
        key = String.valueOf(cursor.getInt(0));
        name = cursor.getString(1);
        description = cursor.getString(2);
        companyKey = cursor.getString(3);
        shu = cursor.getString(4);
        hotness = cursor.getString(5);
        pepper = cursor.getString(6);
        image = cursor.getString(7);
    }

    public Sauce(String name, String description, String companyKey, String shu, String hotness, String pepper, String image) {
        this.name = name;
        this.description = description;
        this.companyKey = companyKey;
        this.shu = shu;
        this.hotness = hotness;
        this.pepper = pepper;
        this.image = image;
    }

    public static void load(SQLiteDatabase db, JSONArray sauces) {
        if (sauces == null)
            return;
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < sauces.length(); i++) {
                JSONObject sauce = sauces.getJSONObject(i);
                values.put(COL.SAUCE_KEY.toString(), sauce.getString(COL.SAUCE_KEY.toString()));
                values.put(COL.NAME.toString(), sauce.getString(COL.NAME.toString()));
                values.put(COL.DESCRIPTION.toString(), sauce.getString(COL.DESCRIPTION.toString()));
                values.put(COL.COMPANY_KEY.toString(), sauce.getString(COL.COMPANY_KEY.toString()));
                values.put(COL.SHU.toString(), sauce.getString(COL.SHU.toString()));
                values.put(COL.HOTNESS.toString(), sauce.getString(COL.HOTNESS.toString()));
                values.put(COL.PEPPER.toString(), sauce.getString(COL.PEPPER.toString()));
                values.put(COL.IMAGE.toString(), sauce.getString(COL.IMAGE.toString()));
                long row = db.insert(TABLE, null, values);
                if (row == -1) {
                    throw new Exception("db.insert received a -1");
                }
            }
            db.close();
        } catch (Exception e) {
            //TODO Something
            System.out.println(e);
        }
    }

    //http://stackoverflow.com/questions/27498763/create-a-separate-database-class-android
    public static List<Sauce> getSauceByKeyword(String keyWord) {
        List<Sauce> sauces = new ArrayList<>();
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SAUCE_BY_KEYWORD_AVG_RATING,
                new String[]{DB.contains(keyWord), DB.contains(keyWord), DB.contains(keyWord), DB.contains(keyWord)});
//        Cursor cursor = db.rawQuery(SELECT_SAUCE_BY_KEYWORD,
//                new String[]{DB.contains(keyWord), DB.contains(keyWord), DB.contains(keyWord), DB.contains(keyWord)});
        if (cursor.moveToFirst()) {
            do {
                Sauce sauce = new Sauce(cursor);
                sauce.addRating(cursor, 8);
                sauce.addRatingCount(cursor, 9);
                sauces.add(sauce);
            } while (cursor.moveToNext());
        }
        return sauces;
    }

    public static Sauce getSauceByKey(String key) {
        Sauce sauce = null;
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SAUCE_BY_KEY_AVG_RATING + key, null);//
        if (cursor.moveToFirst()) {
            sauce = new Sauce(cursor);
            sauce.addRating(cursor, 8);
            sauce.addRatingCount(cursor, 9);
        }
        return sauce;
    }

    public static Sauce getRandomSauce(String hotness) {
        Sauce sauce = null;
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_RANDOM_SAUCE, new String[]{hotness, hotness});
        if (cursor.moveToFirst()) {
            sauce = new Sauce(cursor);
        }
        return sauce;
    }

    public static Sauce getRandomSauce(String hotness, String hotness2) {
        Sauce sauce = null;
        SQLiteDatabase db = G.db.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_RANDOM_SAUCE2, new String[]{hotness, hotness2});
        if (cursor.moveToFirst()) {
            sauce = new Sauce(cursor);
        }
        return sauce;
    }

    private void addRating(Cursor cursor, int index) {
        String rating = cursor.getString(index);
        extra.put(Review.COL.RATING.toString(), rating == null ? "0" : rating);
    }

    private void addRatingCount(Cursor cursor, int index) {
        String ratingCount = cursor.getString(index);
        extra.put(RATING_COUNT, ratingCount == null ? "0" : ratingCount);
    }

    public void save() {
        G.db.getWritableDatabase().execSQL(INSERT, new String[]{name, description,
            companyKey, shu, hotness.toString(), pepper, image});
    }

    public void deleteSauce(Sauce sauce) {
    }

    public void deleteAllSauces() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotness() {
        return hotness;
    }

    public void setHotness(String hotness) {
        this.hotness = hotness;
    }

    public String getPepper() {
        return pepper;
    }

    public void setPepper(String pepper) {
        this.pepper = pepper;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShu() {
        return shu;
    }

    public void setShu(String shu) {
        this.shu = shu;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
