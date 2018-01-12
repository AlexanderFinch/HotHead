package com.finch.hothead;

/**
 * Created by James on 7/9/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.finch.hothead.db.DB;
import com.finch.hothead.db.tables.Banner;
import com.finch.hothead.db.UserHandler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_splash);

        if (G.db == null) {
            G.db = new DB(this, getAssets());
        }

        G.user = UserHandler.load(this);

        G.banners = Banner.getBanners();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}