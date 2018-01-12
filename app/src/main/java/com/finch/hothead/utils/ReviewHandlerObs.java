package com.finch.hothead.utils;

import android.content.ContextWrapper;

import com.finch.hothead.db.tables.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by finchrat on 7/26/2016.
 */
public class ReviewHandlerObs {
    private static String reviewFile = "reviews_file.json";

    public static void save(ContextWrapper contextWrapper, List<Review> reviews) {
        try {
            IOUtils.saveJsonToInternal(contextWrapper, jsonize(reviews), reviewFile);
        } catch (Exception e) {
            // TODO log something?
        }
    }

    public static void delete(ContextWrapper contextWrapper){
        IOUtils.deleteFromInternal(contextWrapper, reviewFile);
    }

    public static List<Review> load(ContextWrapper contextWrapper) {
        List<Review> reviews = new LinkedList<>();
        try {
            JSONObject jObjReviews = IOUtils.getJsonFromInternal(contextWrapper, reviewFile);
            reviews = dejsonize(jObjReviews);
        } catch (JSONException | IOException e) {
            // TODO log something?
        }
        return reviews;
    }

    private static JSONObject jsonize(List<Review> reviews) {
        JSONObject jObj = new JSONObject();
        try {
            JSONArray jObjReviews = new JSONArray();
            if (reviews != null && reviews.size() > 0) {
                for (int i = 0; i <  reviews.size(); i++) {
                    JSONObject jObjReview = new JSONObject();
                    jObjReview.put("comments", reviews.get(i).getComments());
                    jObjReview.put("rating", reviews.get(i).getRating());
                    Date date = reviews.get(i).getDateReviewed();
                    jObjReview.put("dateReviewed", date == null ? "" : date);
                    jObjReview.put("hotSauceKey", reviews.get(i).getSauceKey());
                    jObjReview.put("userKey", reviews.get(i).getUserKey());
                    jObjReviews.put(jObjReview);
                }
            }
            jObj.put("reviews", jObjReviews);
        } catch (JSONException e) {
            //TODO something with the exception
        }
        return jObj;
    }

    private static List<Review> dejsonize(JSONObject jsonObject) throws JSONException {
        List<Review> reviews = new LinkedList<>();
        if (jsonObject != null && jsonObject.has("reviews")) {
            JSONArray jArrayReviews = jsonObject.getJSONArray("reviews");
            for (int i = 0; i < jArrayReviews.length(); i++) {
                JSONObject jObj = jArrayReviews.getJSONObject(i);
                Review review = new Review(
                        jObj.getString("userKey"),
                        jObj.getString("hotSauceKey"),
                        jObj.getString("comments"),
                        jObj.getInt("rating"));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
