package com.imdea.fioravantti.guido.ecousin_ptat;

import android.app.Application;
import android.util.Log;

/**
 * Created by guido on 12/27/14.
 */
public class eCOUSINPTATApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(Constants.control, Constants.appName + " is starting...");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.wtf(Constants.control, Constants.appName + " is running out of memory!");
    }
}
