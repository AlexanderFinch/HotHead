package com.finch.hothead;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.fragments.BookmarkItemFragment;

public class AllBookmarksActivity extends AppCompatActivity {
    public static String TAG = "AllBookmarks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_review_list);

        // setup user view
//        if (savedInstanceState == null) {

        int bookmarkSize = G.bookmarks != null ? G.bookmarks.size() : 0;
        if (bookmarkSize > 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fragment_bookmark_saved);
            linearLayout.removeAllViews();
            for (int i = 0; i < bookmarkSize; i++) {
                Bookmark bookmark = G.bookmarks.get(i);
                BookmarkItemFragment bookmarkItemFragment = new BookmarkItemFragment();
                bookmarkItemFragment.setArguments(
                        BookmarkItemFragment.setBundle(
                                bookmark.getSauceKey()));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_bookmark_saved, bookmarkItemFragment).commit();
            }
        }
//        } else {
//            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.review_fragment_your_sauces);
//            linearLayout1.removeView(findViewById(R.id.review_item_empty));
//        }
    }
}