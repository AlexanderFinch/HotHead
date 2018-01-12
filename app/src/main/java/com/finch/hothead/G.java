package com.finch.hothead;

import com.finch.hothead.db.DB;
import com.finch.hothead.db.tables.Banner;
import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.db.tables.Review;
import com.finch.hothead.db.tables.User;
import com.finch.hothead.fragments.DiscoverFragment;
import com.finch.hothead.fragments.ProfileFragment;
import com.finch.hothead.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * class for global vars
 * Created by finchrat on 7/19/2016.
 */
public class G {
    public static DB db;
    public static User user = new User();
    public static List<Review> reviews = new ArrayList<>();
    public static List<Bookmark> bookmarks = new ArrayList<>();
    public static List<Banner> banners = new ArrayList<>();
    public static final String[] tabOrder = {DiscoverFragment.TAG, ProfileFragment.TAG, SearchFragment.TAG};
    private static boolean[] isDirty = new boolean[tabOrder.length];
    private static int pageSelected = 0;

    public static int tabIndexOf(String tag) {
        int length = tabOrder.length;
        for (int i = 0; i < length; i++) {
            if (tag.equals(tabOrder[i]))
                return i;
        }
        return 0;
    }

    public static boolean isDirty(String tag){
        return isDirty[tabIndexOf(tag)];
    }

    public static void setIsDirty(String tag, boolean dirty) {
        isDirty[tabIndexOf(tag)] = dirty;
    }

    public static void setAllIsDirty() {
        for(int i = 0; i < tabOrder.length; i++){
            isDirty[i] = true;
        }
        G.refresh();
    }

    public static void refresh(){
        G.reviews = Review.getUserReviews();
        G.bookmarks = Bookmark.getUserBookmarks();
    }

    public static int getPageSelected() {
        return pageSelected;
    }

    public static void setPageSelected(int pageSelected) {
        if(pageSelected >= 0 && pageSelected < tabOrder.length) {
            G.pageSelected = pageSelected;
        }
    }
}
