package com.inei.supervision;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.inei.supervision.service.GpsTrackerService;


public class SupervisionApplication extends Application {
    private static final String TAG = SupervisionApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        Log.v(TAG, "Start Service");
        Intent intent = new Intent(SupervisionApplication.this, GpsTrackerService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(intent);*/
    }


    @Override
    public void onTerminate() {
        super.onTerminate();/*
        Log.v(TAG, "End Service");
        stopService(new Intent(SupervisionApplication.this, GpsTrackerService.class));
        */
    }


}
