package com.sxq.database;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Created by SXQ on 2017/6/8.
 */

public class App extends Application {

    private static App INSTANCE ;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
