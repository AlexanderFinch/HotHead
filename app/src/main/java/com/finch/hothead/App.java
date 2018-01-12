package com.finch.hothead;

/**
 * Created by James on 7/9/2016.
 */
import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO Remove the sleep
        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}