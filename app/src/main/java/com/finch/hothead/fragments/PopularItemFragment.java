package com.finch.hothead.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finch.hothead.R;
import com.finch.hothead.utils.RatingWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by finchrat on 7/9/2016.
 */
public class PopularItemFragment extends Fragment {
    public static final String POPULAR = "popular";
    View v;
    // field tags
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String SAUCE_KEY = "sauce_key";
    public static final String COMPANY_NAME = "company_name";
    public static final String RATING = "rating";
    public static final String RATING_COUNT = "rating_count";
    public static final String COMMENTS = "comments";
    public static final String SCREEN_NAME = "screen_name";
    public static final String REST_SERVICE = "/rest/popular";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.item_popular, container, false);
        if (getArguments() != null) {
//            int image = getArguments().getInt(IMAGE);
//            ImageView imageView = (ImageView) v.findViewById(R.id.popular_image); // set source
//            imageView.setImageResource(R.drawable.cl);

            TextView nameView = (TextView) v.findViewById(R.id.popular_name);
            nameView.setText(getArguments().getString(NAME));

            TextView keyView = (TextView) v.findViewById(R.id.key);
            keyView.setText(getArguments().getString(SAUCE_KEY));

            TextView companyView = (TextView) v.findViewById(R.id.popular_company_name);
            companyView.setText(getArguments().getString(COMPANY_NAME));

            LinearLayout ratingWidget = (LinearLayout) v.findViewById(R.id.widget_rating_layout);
            RatingWidget.setRatingStars(ratingWidget, getArguments().getString(RATING), getArguments().getString(RATING_COUNT));

            String comments = getArguments().getString(COMMENTS);
            String user = getArguments().getString(SCREEN_NAME);
            TextView commentsView = (TextView) v.findViewById(R.id.popular_comments);
            commentsView.setText(comments);

            TextView userName = (TextView) v.findViewById(R.id.popular_user_name);
            userName.setText(user);
        }
        return v;
    }

    public Bundle setBundle(JSONObject row) throws JSONException {
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE, row.getString(PopularItemFragment.IMAGE));
        bundle.putString(NAME, row.getString(PopularItemFragment.NAME));
        bundle.putString(SAUCE_KEY, row.getString(PopularItemFragment.SAUCE_KEY));
        bundle.putString(COMPANY_NAME, row.getString(PopularItemFragment.COMPANY_NAME));
        bundle.putString(RATING, row.getString(PopularItemFragment.RATING));
        bundle.putString(RATING_COUNT, row.getString(PopularItemFragment.RATING_COUNT));
        bundle.putString(COMMENTS, row.getString(PopularItemFragment.COMMENTS));
        bundle.putString(SCREEN_NAME, row.getString(PopularItemFragment.SCREEN_NAME));
        return bundle;
    }

    // queries
    public static List<Map<String, String>> getPopularReviews(Context context) {
        //SELECT_RECENT_REVIEWS
//        return DB.getResultList(context, POPULAR);
        return new ArrayList<>();
    }
}
