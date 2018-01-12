package com.finch.hothead.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finch.hothead.R;
import com.finch.hothead.db.tables.Sauce;
import com.finch.hothead.utils.RatingWidget;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by finchrat on 7/9/2016.
 */
public class SearchItemFragment extends Fragment {
    View v;

    public static final String SAUCE_KEY = "sauce_key";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String COMPANY_NAME = "company_name";
    public static final String SHU = "shu";
    public static final String HOTNESS = "hotness";
    public static final String PEPPER = "pepper";
    public static final String RATING = "rating";
    public static final String RATING_COUNT = "rating_count";
    public static final String IMAGE = "image";
    public static final String REST_SERVICE = "/rest/search/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.item_search, container, false);
        if (getArguments() != null) {
            int image = getArguments().getInt(IMAGE);
            ImageView imageView = (ImageView) v.findViewById(R.id.search_item_image); // set source
            imageView.setImageResource(R.mipmap.hotsauce_clear);

            String name = getArguments().getString(NAME);
            TextView nameView = (TextView) v.findViewById(R.id.search_item_name); // set source
            nameView.setText(name);

            String key = getArguments().getString(SAUCE_KEY);
            TextView commentsView = (TextView) v.findViewById(R.id.key); // set source
            commentsView.setText(key);

            String company = getArguments().getString(COMPANY_NAME);
            TextView companyView = (TextView) v.findViewById(R.id.search_item_company_name); // set source
            companyView.setText(company.toString());

//            String pepper = getArguments().getString(PEPPER);
//            TextView pepperView = (TextView) v.findViewById(R.id.sea); // set source
//            pepperView.setText(pepper.toString());

            LinearLayout ratingWidget = (LinearLayout) v.findViewById(R.id.widget_rating_layout);
            Sauce sauce = Sauce.getSauceByKey(key);
            RatingWidget.setRatingStars(ratingWidget, getArguments().getString(RATING), getArguments().getString(RATING_COUNT));
        }
        return v;
    }

    public static Bundle setBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        bundle.putString(SAUCE_KEY, jsonObject.getString(SAUCE_KEY));
//        bundle.putString(IMAGE, jsonObject.getString(IMAGE));
        bundle.putString(NAME, jsonObject.getString(NAME));
        bundle.putString(COMPANY_NAME, jsonObject.getString(COMPANY_NAME));
//        bundle.putString(PEPPER, jsonObject.getString(PEPPER));
        bundle.putString(RATING, jsonObject.getString(RATING));
        bundle.putString(RATING_COUNT, jsonObject.getString(RATING_COUNT));
        return bundle;
    }
}
