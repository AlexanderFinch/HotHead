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

import com.finch.hothead.R;
import com.finch.hothead.rest.RestGetAdapter;
import com.finch.hothead.view.HScrollView;
import com.finch.hothead.view.RefreshScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DiscoverFragment extends Fragment {

    public HScrollView popular;
    public LinearLayout popularInd;
    public HScrollView recommended;
    public LinearLayout recommendedInd;
    public static String TAG = "Discover";
    private static String TAG_RECOMMENDED = "recommended";
    private static String TAG_POPULAR = "popular";
    private LBMReceiver r;
    private LinearLayout popularLayout;
    private LinearLayout recommendedLayout;

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
        final RefreshScrollView view = (RefreshScrollView) inflater.inflate(R.layout.fragment_discover, container, false);
        view.setUpdateCallback(new RefreshScrollView.Callback() {
            @Override
            public void update() {
                refresh();
            }
        });

        // setup the HScrollView so that it will pop to the center of the items
        recommended = (HScrollView) view.findViewById(R.id.recommended_view);
        recommendedInd = (LinearLayout) view.findViewById(R.id.recommended_indicator);
        recommended.setUpdateCallback(new HScrollView.UpdateCallback() {
            @Override
            public void update() {
                int currentChildPosition = recommended.getChildViewPosition();
                int childCount = recommendedInd.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == currentChildPosition) {
                        recommendedInd.getChildAt(i).setBackgroundResource(R.drawable.item_indicator_selected_primary);
                    } else {
                        recommendedInd.getChildAt(i).setBackgroundResource(R.drawable.item_indicator_open_primary);
                    }
                }
            }
        });
        recommendedLayout = (LinearLayout) view.findViewById(R.id.recommended_layout_discover);
        recommended.setLinearLayout(recommendedLayout);

        // load available banners
        RestGetAdapter raRec = new RestGetAdapter(this.getContext(), RecommendedItemFragment.REST_SERVICE) {
            @Override
            protected void success(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.getString("response"));
                    if (jsonArray != null && jsonArray.length() > 0) {
                        recommendedLayout.removeAllViews();
                        int position = 0;
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            RecommendedItemFragment recommendedItemFragment = new RecommendedItemFragment();
                            DiscoverFragment.this.getFragmentManager().beginTransaction().add(R.id.recommended_layout_discover, recommendedItemFragment).commit();
                            recommendedItemFragment.setArguments(
                                    RecommendedItemFragment.setBundle(row));
                            PageIndicatorFragment fragment = new PageIndicatorFragment();
                            fragment.setUpdateCallback(new PageIndicatorFragment.UpdateCallback() {
                                private int position;
                                private HScrollView view;

                                @Override
                                public void update() {
                                    view.scrollTo(position);
                                }

                                private PageIndicatorFragment.UpdateCallback init(int position, HScrollView view) {
                                    this.position = position;
                                    this.view = view;
                                    return this;
                                }
                            }.init(position++, recommended));
                            DiscoverFragment.this.getFragmentManager().beginTransaction().add(R.id.recommended_indicator, fragment, TAG_RECOMMENDED).commit();
                        }

                        recommended.scrollToBeginning();
                    }
                } catch (JSONException e) {
                    // todo something
                }
            }

            @Override
            protected void error() {

            }
        };
        raRec.execute();


        // setup the HScrollView so that it will pop to the center of the items
        popular = (HScrollView) view.findViewById(R.id.popular_view);
        popularInd = (LinearLayout) view.findViewById(R.id.popular_indicator);
        popular.setUpdateCallback(new HScrollView.UpdateCallback() {
            @Override
            public void update() {
                int currentChildPosition = popular.getChildViewPosition();
                int childCount = popularInd.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == currentChildPosition) {
                        popularInd.getChildAt(i).setBackgroundResource(R.drawable.item_indicator_selected_primary);
                    } else {
                        popularInd.getChildAt(i).setBackgroundResource(R.drawable.item_indicator_open_primary);
                    }
                }
            }
        });
        popularLayout = (LinearLayout) view.findViewById(R.id.popular_layout);
        popular.setLinearLayout(popularLayout);

        RestGetAdapter raPop = new RestGetAdapter(this.getContext(), PopularItemFragment.REST_SERVICE) {
            @Override
            protected void success(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.getString("response"));

                    if (jsonArray != null && jsonArray.length() > 0) {
                        int position = 0;
                        popularLayout.removeAllViews();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject row = jsonArray.getJSONObject(i);
                            final PopularItemFragment popularItemFragment = new PopularItemFragment();
                            popularItemFragment.setArguments(popularItemFragment.setBundle(row));
                            DiscoverFragment.this.getFragmentManager().beginTransaction().add(R.id.popular_layout, popularItemFragment, TAG_POPULAR).commit();
                            PageIndicatorFragment fragment = new PageIndicatorFragment();
                            fragment.setUpdateCallback(new PageIndicatorFragment.UpdateCallback() {
                                private int position;
                                private HScrollView view;

                                @Override
                                public void update() {
                                    view.scrollTo(position);
                                }

                                private PageIndicatorFragment.UpdateCallback init(int position, HScrollView view) {
                                    this.position = position;
                                    this.view = view;
                                    return this;
                                }
                            }.init(position++, popular));
                            DiscoverFragment.this.getFragmentManager().beginTransaction().add(R.id.popular_indicator, fragment).commit();
                        }

                        popular.scrollToBeginning();
                    }
                } catch (JSONException e) {
                    //todo something here
                }
            }

            @Override
            protected void error() {

            }
        };
        raPop.execute();

        return view;
    }

    private void removeAllFragments(String tag) {
        List<Fragment> fragments = this.getFragmentManager().getFragments();
        if (fragments == null) {
            for (Fragment frag : fragments) {
                if (frag.getTag().equals(tag)) {
                    this.getFragmentManager().beginTransaction().remove(frag).commit();
                }
            }
        }
    }

    private void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public class LBMReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DiscoverFragment.this.refresh();
        }
    }
}