package com.finch.hothead.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.finch.hothead.R;
import com.finch.hothead.rest.RestGetAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by finchrat on 7/9/2016.
 */
public class SearchFragment extends Fragment {
    public static String TAG = "Find";
    private SearchView searchView;
    private View addSauceView;
    View v;
    private LBMReceiver r;
    private LinearLayout linearLayout;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);
        addSauceView = inflater.inflate(R.layout.item_search_add_sauce, container, false);

        linearLayout = (LinearLayout) v.findViewById(R.id.search_results);

        searchView = (SearchView) v.findViewById(R.id.sauce_search_view);
        searchView.setIconifiedByDefault(false);
        // Catch event on [x] button inside search view
        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchView.findViewById(searchCloseButtonId);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                linearLayout.removeAllViews();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Handler handler = new Handler();
            private String searchTerm;

            @Override
            public boolean onQueryTextSubmit(String s) {
                if (searchView != null) {
                    searchView.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                searchTerm = s;
//                handler.removeCallbacksAndMessages(null);
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        doSearch(searchTerm);
//                    }
//                }, 500);
                return false;
            }
        });

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.search_results);
        linearLayout.removeAllViews();
        return v;
    }

    private void doSearch(String query) {
        RestGetAdapter ra = new RestGetAdapter(this.getContext(), SearchItemFragment.REST_SERVICE) {
            @Override
            protected void success(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.getString("response"));
                    if (jsonArray != null && jsonArray.length() > 0) {
                        linearLayout.removeAllViews();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            SearchItemFragment searchItemFragment = new SearchItemFragment();
                            searchItemFragment.setArguments(SearchItemFragment.setBundle(row));
                            SearchFragment.this.getFragmentManager().beginTransaction().add(R.id.search_results, searchItemFragment).commit();
                        }
                        linearLayout.addView(addSauceView);
                    }
                } catch (JSONException e) {
                    // todo something
                }
            }

            @Override
            protected void error() {

            }
        };
        ra.execute(query);
    }

    private void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public class LBMReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SearchFragment.this.refresh();
        }
    }
}
