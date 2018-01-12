package com.finch.hothead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finch.hothead.rest.RestGetAdapter;
import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.db.tables.Review;
import com.finch.hothead.fragments.PopularItemFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by finchrat on 7/9/2016.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DETAIL";

    private static final String REST_SERVICE_RND = "/rest/random/";
    private static final String REST_SERVICE_DETAIL = "/rest/detail/";
    private static final String REST_SERVICE_REV = "/rest/reviews/";

    private static final String IMAGE = "image";
    private static final String NAME = "name";
    private static final String SAUCE_KEY = "sauce_key";
    private static final String COMPANY_NAME = "company_name";
    private static final String PEPPER = "pepper";
    private static final String HOTNESS = "hotness";
    private static final String SHU = "shu";
    private static final String DESCRIPTION = "description";

    private TextView keyView;
    private Float rating = 0f;
    private int toggleStar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        keyView = (TextView) findViewById(R.id.key);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get("key") != null) {
            String key = bundle.getString("key");
            RestGetAdapter ra = new RestGetAdapter(DetailActivity.this, DetailActivity.REST_SERVICE_DETAIL) {
                @Override
                protected void success(JSONObject response) {
                    setFields(response);
                }

                @Override
                protected void error() {

                }
            };
            ra.execute(key);

        } else if (bundle != null && bundle.get("hotness") != null) {
            String hotness = bundle.getString("hotness");
            RestGetAdapter ra = new RestGetAdapter(DetailActivity.this, DetailActivity.REST_SERVICE_RND) {
                @Override
                protected void success(JSONObject response) {
                    setFields(response);
                }

                @Override
                protected void error() {

                }
            };
            ra.execute(hotness);
        }
    }

    private void setFields(JSONObject res) {
        try {
            JSONArray jsonArray = new JSONArray(res.getString("response"));
            JSONObject response = jsonArray.getJSONObject(0);

            ImageView imageView = (ImageView) findViewById(R.id.detail_image);
            imageView.setImageResource(R.mipmap.hotsauce_clear);

            TextView nameView = (TextView) findViewById(R.id.detail_name);
            nameView.setText(response.getString(NAME));
            nameView.requestFocus();

            keyView.setText(response.getString(SAUCE_KEY));

            TextView companyView = (TextView) findViewById(R.id.detail_company_name);
            companyView.setText(response.getString(COMPANY_NAME));

            TextView pepperView = (TextView) findViewById(R.id.detail_pepper);
            pepperView.setText(response.getString(PEPPER));

            TextView hotnessView = (TextView) findViewById(R.id.detail_hotness);
            hotnessView.setText("(");
            hotnessView.append(response.getString(HOTNESS));
            hotnessView.append(")");

            String shu = response.getString(SHU);
            LinearLayout shuLayout = (LinearLayout) findViewById(R.id.detail_shu_layout);
            if ((shu == null || shu.isEmpty()) && shuLayout != null) {
                ((LinearLayout) shuLayout.getParent()).removeView(shuLayout);
            } else {
                TextView shuView = (TextView) findViewById(R.id.detail_shu);
                shuView.setText(shu);
            }

            TextView descView = (TextView) findViewById(R.id.detail_description);
            descView.setText(response.getString(DESCRIPTION));

//                LinearLayout ratingWidget = (LinearLayout) findViewById(R.id.widget_rating_layout);
//                RatingWidget.setRatingStars(ratingWidget, sauce, sauce.getExtra()
//                        .get(Review.COL.RATING.toString())));

            RestGetAdapter raRev = new RestGetAdapter(DetailActivity.this, DetailActivity.REST_SERVICE_REV ) {
                @Override
                protected void success(JSONObject response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.getString("response"));
                        if (jsonArray != null && jsonArray.length() > 0) {
                            LinearLayout recentReviewLayout = (LinearLayout) findViewById(R.id.detail_recent_reviews_layout);
                            recentReviewLayout.removeAllViews();
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject row = jsonArray.getJSONObject(i);
                                final PopularItemFragment popularItemFragment = new PopularItemFragment();
                                popularItemFragment.setArguments(
                                        popularItemFragment.setBundle(row));
                                getSupportFragmentManager().beginTransaction().add(R.id.detail_recent_reviews_layout, popularItemFragment).commit();
                            }
                        }
                    } catch (JSONException e) {
                        // todo something
                    }
                }

                @Override
                protected void error() {

                }
            };
            raRev.execute(DetailActivity.REST_SERVICE_REV + response.getString(SAUCE_KEY));
        } catch (JSONException e) {
            // todo something
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addReview(View view) {
        if (G.user != null && !G.user.isEmpty()) {
            TextView commentsView = (TextView) findViewById(R.id.widget_review_comments);
            Review review = new Review(keyView.getText().toString(), G.user.getKey(), commentsView.getText().toString(), rating);
            review.save();
            G.setAllIsDirty();
            finish();
        } else {
//            popup a new window saying please log in and shit
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage(R.string.text_login_first)
                    .setPositiveButton(R.string.text_log_in, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(keyView.getContext(), ProfileEditActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }

    public void setRating(View view) {
        LinearLayout yourReview = (LinearLayout) view.getRootView().findViewById(R.id.widget_your_review);
        if (yourReview == null) {
            appendReviewView(view.getRootView().findViewById(R.id.your_review));
        }

        int id = view.getId();

        ImageView button1 = (ImageView) findViewById(R.id.widget_your_rating_1);
        ImageView button2 = (ImageView) findViewById(R.id.widget_your_rating_2);
        ImageView button3 = (ImageView) findViewById(R.id.widget_your_rating_3);
        ImageView button4 = (ImageView) findViewById(R.id.widget_your_rating_4);
        ImageView button5 = (ImageView) findViewById(R.id.widget_your_rating_5);
        int idOn = getResources().getIdentifier("android:drawable/btn_star_big_on", null, null);
        int idOff = getResources().getIdentifier("android:drawable/btn_star_big_off", null, null);
        if (id == toggleStar) {
            button1.setImageResource(idOff);
            button2.setImageResource(idOff);
            button3.setImageResource(idOff);
            button4.setImageResource(idOff);
            button5.setImageResource(idOff);
            toggleStar = 0;
        } else {
            toggleStar = id;
            if (id == R.id.widget_your_rating_1) {
                button1.setImageResource(idOn);
                button2.setImageResource(idOff);
                button3.setImageResource(idOff);
                button4.setImageResource(idOff);
                button5.setImageResource(idOff);
                rating = 1f;
            } else if (id == R.id.widget_your_rating_2) {
                button1.setImageResource(idOn);
                button2.setImageResource(idOn);
                button3.setImageResource(idOff);
                button4.setImageResource(idOff);
                button5.setImageResource(idOff);
                rating = 2f;
            } else if (id == R.id.widget_your_rating_3) {
                button1.setImageResource(idOn);
                button2.setImageResource(idOn);
                button3.setImageResource(idOn);
                button4.setImageResource(idOff);
                button5.setImageResource(idOff);
                rating = 3f;
            } else if (id == R.id.widget_your_rating_4) {
                button1.setImageResource(idOn);
                button2.setImageResource(idOn);
                button3.setImageResource(idOn);
                button4.setImageResource(idOn);
                button5.setImageResource(idOff);
                rating = 4f;
            } else if (id == R.id.widget_your_rating_5) {
                button1.setImageResource(idOn);
                button2.setImageResource(idOn);
                button3.setImageResource(idOn);
                button4.setImageResource(idOn);
                button5.setImageResource(idOn);
                rating = 5f;
            }
        }
    }

    public void addBookmark(View view) {
        final TextView keyView = (TextView) view.getRootView().findViewById(R.id.key);
        final TextView nameView = (TextView) view.getRootView().findViewById(R.id.detail_name);
        if (G.user != null && !G.user.isEmpty()) {
            Bookmark bookmark = new Bookmark(keyView.getText().toString(), G.user.getKey());
            if (bookmark.save() == 1) {
                G.setAllIsDirty();
                Toast.makeText(getApplicationContext(), nameView.getText() + " bookmarked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), nameView.getText() + " previously bookmarked", Toast.LENGTH_SHORT).show();
            }
        } else {
//            popup a new window saying please log in and shit
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage(R.string.text_login_first)
                    .setPositiveButton(R.string.text_log_in, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(keyView.getContext(), ProfileEditActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }

    public void appendReviewView(View view) {
        LinearLayout yourReviewWidget = (LinearLayout) view.getRootView().findViewById(R.id.widget_your_review);
        if (yourReviewWidget == null) {
            LinearLayout yourReview = (LinearLayout) view.getRootView().findViewById(R.id.your_review);
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            inflater.inflate(R.layout.widget_your_review, yourReview);
//            yourReview.requestFocus();
            yourReview.requestFocusFromTouch();
            TextView textView = (TextView) yourReview.findViewById(R.id.your_review_title);
            textView.setText(R.string.text_your_review);
        }
    }

    public void cancelReview(View view) {
        LinearLayout yourReviewWidget = (LinearLayout) view.getRootView().findViewById(R.id.widget_your_review);
        if (yourReviewWidget != null) {
            LinearLayout yourReview = (LinearLayout) view.getRootView().findViewById(R.id.your_review);
            TextView textView = (TextView) yourReview.findViewById(R.id.your_review_title);
            textView.setText(R.string.text_click_to_review);
            EditText comments = (EditText) yourReview.findViewById(R.id.widget_review_comments);
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(comments.getWindowToken(), 0);
            yourReview.removeView(yourReviewWidget);
        }
    }

    public void openShare(View view) {
    }

    public void openSearch(View view) {
        G.setPageSelected(2);
        MainActivity.setCurrentPage(G.getPageSelected());
        finish();
    }
}

