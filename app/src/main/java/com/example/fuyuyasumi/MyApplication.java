package com.example.fuyuyasumi;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangbin on 2016/1/23.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }
}
