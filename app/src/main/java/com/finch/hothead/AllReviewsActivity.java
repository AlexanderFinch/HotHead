package com.finch.hothead;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.finch.hothead.db.tables.Review;
import com.finch.hothead.fragments.ReviewItemFragment;

public class AllReviewsActivity extends AppCompatActivity {
    public static String TAG = "AllReviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_review_list);

        // setup user view
//        if (savedInstanceState == null) {

            int reviewSize = G.reviews != null ? G.reviews.size() : 0;
            // get reviews
            if (reviewSize > 0) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.review_fragment_your_sauces);
                linearLayout.removeAllViews();
                for (int i = 0; i < reviewSize; i++) {
                    Review review = G.reviews.get(i);
                    ReviewItemFragment reviewItemFragment = new ReviewItemFragment();
                    reviewItemFragment.setArguments(
                            ReviewItemFragment.setBundle(
                                    review.getSauceKey(), review.getRating(), review.getComments()));
                    getSupportFragmentManager().beginTransaction().add(R.id.review_fragment_your_sauces, reviewItemFragment).commit();
                }
            }
//        } else {
//            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.review_fragment_your_sauces);
//            linearLayout1.removeView(findViewById(R.id.review_item_empty));
//        }
    }
}