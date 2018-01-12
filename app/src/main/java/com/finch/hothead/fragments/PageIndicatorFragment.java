package com.finch.hothead.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finch.hothead.R;

/**
 * Created by finchrat on 7/9/2016.
 */
public class PageIndicatorFragment extends Fragment implements View.OnClickListener {
    private UpdateCallback updateCallback;
    View v;


    public interface UpdateCallback {
        void update();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.widget_page_indicator, container, false);
        v.setOnClickListener(this);
        return v;
    }

    public void setUpdateCallback(UpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    @Override
    public void onClick(View view) {
        updateCallback.update();
    }
}
