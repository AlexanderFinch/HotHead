package com.finch.hothead;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finch.hothead.db.tables.Review;
import com.finch.hothead.db.UserHandler;
import com.finch.hothead.rest.RestGetAdapter;
import com.finch.hothead.rest.RestPostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by finchrat on 7/9/2016.
 */
public class ProfileEditActivity extends AppCompatActivity {
    private String GET_PROFILE_ENDPOINT = "/rest/getprofile/";
    private String GET_PROFILE_FROM_HASH_ENDPOINT = "/rest/getprofilefromhash/";
    private String CREATE_PROFILE_ENDPOINT = "/rest/createprofile";

    private static final String EMAIL = "name";
    private static final String SCREEN_NAME = "screen_name";

    private EditText email;
    private EditText screenName;
    private EditText firstName;
    private EditText lastName;
    private Button save;
    private InputFilter[] emailFilters = {new InputFilter.LengthFilter(100)};
    private InputFilter[] screenNameFilters = {new InputFilter.LengthFilter(14)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

//        firstName = (EditText) findViewById(R.id.first_name);
//        lastName = (EditText) findViewById(R.id.last_name);
//        // setup email validation
        save = (Button) findViewById(R.id.register_button);
        email = (EditText) findViewById(R.id.email);
        email.setFilters(emailFilters);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && !screenName.getText().toString().isEmpty()) {
                    save.setEnabled(true);
                } else {
                    save.setEnabled(false);
                }
            }
        });

        screenName = (EditText) findViewById(R.id.screen_name);
        screenName.setFilters(screenNameFilters);
        screenName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty() && !email.getText().toString().isEmpty()) {
                    save.setEnabled(true);
                } else {
                    save.setEnabled(false);
                }
            }
        });

        screenName.setText(G.user.getScreenName());
        email.setText(G.user.getEmail());
        if (G.user.isEmpty()) {
//            firstName.setText(G.user.getFirstName());
//            lastName.setText(G.user.getLastName());
            save.setEnabled(false);
            save.setText(R.string.text_register_button_register);
        } else {
            save.setText(R.string.text_register_button_save);
        }
    }

    public void registerUser(View view) {
        if (G.user.isEmpty()) {
            // go see if it is in the db
            RestGetAdapter ra = new RestGetAdapter(screenName.getContext(), GET_PROFILE_FROM_HASH_ENDPOINT) {
                @Override
                protected void success(JSONObject response) {
                    JSONObject res = null;
                    try{
                        JSONArray jsonArray = new JSONArray(response.getString("response"));
                        res = jsonArray.getJSONObject(0);
//                        res = new JSONObject(response.optString("response"));
                    } catch (JSONException e){
                        //todo something
                    }
                    if (res != null && "success".equals(res.optString("success"))) {
                        // if so, load the user from db.
                        G.user.setEmail(res.optString(EMAIL));
                        G.user.setScreenName(res.optString(SCREEN_NAME));
                        G.user.save();
                        setFields(response);
                    } else {
                        // if not, register the user
                        JSONObject data = new JSONObject();
                        try {
                            data.put(SCREEN_NAME, screenName.getText().toString().trim());
                            data.put(EMAIL, email.getText().toString().trim());
                            data.put("hash", G.user.getHash());
                        } catch (JSONException e) {
                            // todo something
                        }
                        RestPostAdapter ra = new RestPostAdapter(screenName.getContext(), CREATE_PROFILE_ENDPOINT, data) {
                            @Override
                            protected void updatePage(JSONObject response) {
                            }
                        };
                        ra.execute();
                    }
                }

                @Override
                protected void error() {
                    errorOccurred();
                }
            };
            ra.execute(G.user.getHash());
//            MessageDigest sha512 = null;
//            try {
//                sha512 = MessageDigest.getInstance("SHA512");
//            } catch (NoSuchAlgorithmException e){
//                //todo something
//            }
//            if(sha512 != null)
//                sha512.update(email.getText().toString().getBytes());

//        User user = new User(
//                screenName.getText().toString(),
//                firstName.getText().toString(),
//                lastName.getText().toString(),
//                email.getText().toString());
//        User user = new User(
//                screenName.getText().toString(),
//                "","","");
//        if (!user.isEmpty() && UserHandler.save(this, user)) {
//            G.setAllIsDirty();
//            finish();
//        } else {
////            popup a new window saying please log in and shit
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
//            alertDialogBuilder.setMessage("Something went wrong.")
//                    .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//
//            // create alert dialog
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            // show it
//            alertDialog.show();
        }
    }

    private void setFields(JSONObject res) {
        try {
            JSONArray jsonArray = new JSONArray(res.getString("response"));
            JSONObject response = jsonArray.getJSONObject(0);

            screenName.setText(response.getString(SCREEN_NAME));
            email.setText(response.getString(EMAIL));
            finish();
        } catch (JSONException e) {

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void deleteUser(View view) {
        UserHandler.delete(this, G.user);
        UserHandler.load(this);
        Intent intent = new Intent(this, MainActivity.class);// have to start new activity to reload the user name in the upper left
        startActivity(intent);
        finish();
    }

    public void deleteReviews(View view) {
        Review.deleteAllReviews();
        finish();
    }

    public void changeAvatar(View view) {
        Toast.makeText(getApplicationContext(), "Image upload coming soon", Toast.LENGTH_SHORT).show();

    }

    public void errorOccurred() {
        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();

    }
}
