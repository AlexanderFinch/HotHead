package com.finch.hothead;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finch.hothead.db.tables.Bookmark;
import com.finch.hothead.db.tables.Sauce;
import com.finch.hothead.fragments.DiscoverFragment;
import com.finch.hothead.fragments.ProfileFragment;
import com.finch.hothead.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter
public class MainActivity extends AppCompatActivity {
    TabAdapter tabAdapter;
    private static ViewPager viewPager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(G.db == null){
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }

        // set this activity as home, set icon in the action bar
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");
        actionBar.setIcon(R.mipmap.hot_head_logo);

        // setup the tabs
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(tabAdapter);
        viewPager.setCurrentItem(G.getPageSelected());
        viewPager.setOffscreenPageLimit(G.tabOrder.length-1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                clearFocus();
            }

            @Override
            public void onPageSelected(int position) {
                G.setPageSelected(position);

                if (G.isDirty(G.tabOrder[position])) {
                    LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(viewPager.getContext());
                    Intent i = new Intent(G.tabOrder[position]);
                    lbm.sendBroadcast(i);
                    G.setIsDirty(G.tabOrder[position], false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if(G.getPageSelected() != 0){
            viewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void clearFocus() {
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            focusedView.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public static int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    public static void setCurrentPage(int i) {
        viewPager.setCurrentItem(i);
    }

    public void openSettings(MenuItem item) {
        Toast.makeText(getApplicationContext(), "you clicked settings", Toast.LENGTH_SHORT).show();
        //TODO make a settings activity open here
    }

    public void refresh(MenuItem item) {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(viewPager.getContext());
        Intent i = new Intent(G.tabOrder[viewPager.getCurrentItem()]);
        lbm.sendBroadcast(i);
    }

    public void openSearch(MenuItem item) {
        openSearch();
    }

    public void openSearch(View view) {
        openSearch();
    }

    private void openSearch() {
        viewPager.setCurrentItem(G.tabIndexOf(SearchFragment.TAG));
    }

    public void openProfile(MenuItem item) {
        openProfile();
    }

    public void openProfile(View view) {
        openProfile();
    }

    public void openHelp(MenuItem item) {
        Toast.makeText(getApplicationContext(), "you clicked help", Toast.LENGTH_SHORT).show();
        //TODO make a help activity open here
    }

    private void openProfile() {
        Intent intent = new Intent(this, ProfileEditActivity.class);
        startActivity(intent);
    }

    public void openDetail(View view) {
        //hide the keyboard
        clearFocus();
        final TextView keyView;
        if (view instanceof ViewGroup) {
            keyView = (TextView) view.findViewById(R.id.key);
        } else {
            keyView = (TextView)((ViewGroup) view.getParent()).findViewById(R.id.key);
        }

        if(keyView != null) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("key", keyView.getText().toString());
            startActivity(intent);
        } else {
//            popup a new window saying please log in and shit
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage("Sauce not found")
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

    public void openDetailRandom(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        int id = view.getId();
        String hotness;
        switch (id){
            case R.id.widget_heat_level_xxxhot:
                hotness = "insane";
                break;
            case R.id.widget_heat_level_mild:
                hotness = "mild";
                break;
            case R.id.widget_heat_level_medium:
                hotness = "medium";
                break;
            case R.id.widget_heat_level_hot:
                hotness = "hot";
                break;
            default:
                hotness = "all";
                break;
        }
        if (hotness != null) {
            intent.putExtra("hotness", hotness);
            startActivity(intent);
        } else {
//            popup a new window saying please log in and shit
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage("No sauces found.")
                    .setNegativeButton(R.string.text_ok, new DialogInterface.OnClickListener() {
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

    public void openAddSauce(View view) {
        Intent intent = new Intent(this, AddSauceActivity.class);
//        intent.putExtra("sauce", "Profile");
        startActivity(intent);
    }

//    public void openAllReviews(View view) {
//        Intent intent = new Intent(this, AllReviewsActivity.class);
//        startActivity(intent);
//    }
//
//    public void openAllBookmarks(View view) {
//        Intent intent = new Intent(this, AllBookmarksActivity.class);
//        startActivity(intent);
//    }

    public void addBookmark(View view) {
        final TextView keyView = (TextView)((LinearLayout) view.getParent().getParent()).findViewById(R.id.key);
        final TextView nameView = (TextView)((LinearLayout) view.getParent().getParent()).findViewById(R.id.popular_name);
        if (G.user != null && !G.user.isEmpty() && keyView != null) {
            Bookmark bookmark = new Bookmark(keyView.getText().toString(), G.user.getKey());
            if(bookmark.save() == 1) {
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

    public static class TabAdapter extends FragmentPagerAdapter {
        private final List<String> titleList = new ArrayList<>(Arrays.asList(G.tabOrder));

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (DiscoverFragment.TAG.equals(titleList.get(position))) {
                fragment = new DiscoverFragment();
            } else if (ProfileFragment.TAG.equals(titleList.get(position))) {
                fragment = new ProfileFragment();
            } else if (SearchFragment.TAG.equals(titleList.get(position))) {
                fragment = new SearchFragment();
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}