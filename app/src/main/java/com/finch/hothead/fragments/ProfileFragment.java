package com.finch.hothead.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finch.hothead.G;
import com.finch.hothead.R;
import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.db.tables.Review;
import com.finch.hothead.view.RefreshScrollView;

public class ProfileFragment extends Fragment {
    public static final int SHOW_REVIEW_COUNT = 30;
    public static String TAG = "Track";
    //    private ViewGroup container;
//    private LayoutInflater inflater;
    private LBMReceiver r;

    @Override
    public void onResume() {
        r = new LBMReceiver();
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(r, new IntentFilter(TAG));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(r);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RefreshScrollView view = (RefreshScrollView) inflater.inflate(R.layout.fragment_profile, container, false);
        view.setUpdateCallback(new RefreshScrollView.Callback() {
            @Override
            public void update() {
                refresh();
            }
        });
        refreshData(view);
        return view;
    }

    private void refreshData(View view) {
        // setup user view
//        if (savedInstanceState == null) {
        if (G.user != null && G.user.getScreenName() != null && G.user.getScreenName().length() > 0) {
            TextView userName = (TextView) view.findViewById(R.id.profile_your_name);
            userName.setText(G.user.getScreenName());// set the users name
        }

        int reviewSize = G.reviews != null ? G.reviews.size() : 0;
        // set the review count
        TextView reviewCount = (TextView) view.findViewById(R.id.profile_your_review_num);
        reviewCount.setText(String.valueOf(reviewSize));

        int showSize = reviewSize > SHOW_REVIEW_COUNT ? SHOW_REVIEW_COUNT : reviewSize;
        // get reviews
        if (showSize > 0) {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.review_fragment_your_sauces);
            linearLayout.removeAllViews();
            for (int i = 0; i < showSize; i++) {
                Review review = G.reviews.get(i);
                ReviewItemFragment reviewItemFragment = new ReviewItemFragment();
                reviewItemFragment.setArguments(
                        ReviewItemFragment.setBundle(
                                review.getSauceKey(), review.getRating(), review.getComments()));
                this.getFragmentManager().beginTransaction().add(R.id.review_fragment_your_sauces, reviewItemFragment).commit();
            }
        }

        int bookmarkSize = G.bookmarks != null ? G.bookmarks.size() : 0;
        // set the bookmark count
        TextView bookmarkCount = (TextView) view.findViewById(R.id.profile_your_bookmark_num);
        bookmarkCount.setText(String.valueOf(bookmarkSize));

        showSize = bookmarkSize > SHOW_REVIEW_COUNT ? SHOW_REVIEW_COUNT : bookmarkSize;
        // get bookmarks
        if (showSize > 0) {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_bookmark_saved);
            linearLayout.removeAllViews();
            for (int i = 0; i < showSize; i++) {
                Bookmark bookmark = G.bookmarks.get(i);
                BookmarkItemFragment bookmarkItemFragment = new BookmarkItemFragment();
                bookmarkItemFragment.setArguments(
                        BookmarkItemFragment.setBundle(
                                bookmark.getSauceKey()));
                this.getFragmentManager().beginTransaction().add(R.id.fragment_bookmark_saved, bookmarkItemFragment).commit();
            }
        }
//        } else {
//            LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.review_fragment_your_sauces);
//            linearLayout1.removeView(view.findViewById(R.id.review_item_empty));
//            LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.fragment_bookmark_saved);
//            linearLayout2.removeView(view.findViewById(R.id.bookmark_item_empty));
//        }
    }

    private void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public class LBMReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProfileFragment.this.refresh();
        }
    }

}