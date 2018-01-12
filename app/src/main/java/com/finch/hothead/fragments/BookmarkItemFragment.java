package com.finch.hothead.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finch.hothead.R;
import com.finch.hothead.db.tables.Sauce;
import com.finch.hothead.utils.RatingWidget;

/**
 * Created by finchrat on 7/9/2016.
 */
public class BookmarkItemFragment extends Fragment {
    View v;

    private static final String KEY = "key";
    private static final String RATING = "rating";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.item_bookmark, container, false);
        if (getArguments() != null) {
            String key = getArguments().getString(KEY);
            Sauce sauce = Sauce.getSauceByKey(key);
            if (sauce != null) {
//            int image = getArguments().getInt("image");
//            ImageView imageView = (ImageView) v.findViewById(R.id.review_image); // set source
//            imageView.setImageResource(R.drawable.hot_sauce1_remove);

                TextView nameView = (TextView) v.findViewById(R.id.bookmark_item_name); // set source
                nameView.setText(sauce.getName());

                TextView companyView = (TextView) v.findViewById(R.id.bookmark_item_company_name); // set source
                companyView.setText(sauce.getCompanyKey());

                TextView keyView = (TextView) v.findViewById(R.id.key); // set source
                keyView.setText(key);

                Float rating = getArguments().getFloat(RATING);
                LinearLayout ratingWidget = (LinearLayout) v.findViewById(R.id.widget_rating_layout);
//                RatingWidget.setRatingStars(ratingWidget, sauce, rating);
            }
        }
        return v;
    }

    public static Bundle setBundle(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, key);
        return bundle;
    }
}
