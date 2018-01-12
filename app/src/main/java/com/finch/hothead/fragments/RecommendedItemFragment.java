package com.finch.hothead.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finch.hothead.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by finchrat on 7/9/2016.
 */
public class RecommendedItemFragment extends Fragment {
    View v;

    public static final String NAME = "name";
    public static final String COMPANY_NAME = "company_name";
    public static final String MESSAGE = "message";
    public static final String SAUCE_KEY = "sauce_key";
    public static final String REST_SERVICE = "/rest/recommended";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.item_recommended, container, false);
        if(getArguments() != null) {
            TextView nameView = (TextView) v.findViewById(R.id.recommended_item_name);
            nameView.setText(getArguments().getString(NAME));

            TextView companyView = (TextView) v.findViewById(R.id.recommended_item_company_name);
            companyView.setText(getArguments().getString(COMPANY_NAME));

            TextView messageView = (TextView) v.findViewById(R.id.recommended_item_message);
            messageView.setText(getArguments().getString(MESSAGE));

            TextView keyView = (TextView) v.findViewById(R.id.key);
            keyView.setText(getArguments().getString(SAUCE_KEY));

            Button button = (Button) v.findViewById(R.id.recommended_button);
            button.setTag(getArguments().getString(SAUCE_KEY));

            int image = getArguments().getInt("image");
            v.setBackgroundResource(R.drawable.background_hot_sauce1);
        }
        return v;
    }

    public static Bundle setBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        bundle.putString(NAME, jsonObject.getString(NAME));
        bundle.putString(COMPANY_NAME, jsonObject.getString(COMPANY_NAME));
        bundle.putString(MESSAGE, jsonObject.getString(MESSAGE));
        bundle.putString(SAUCE_KEY, jsonObject.getString(SAUCE_KEY));
        return bundle;
    }
}
