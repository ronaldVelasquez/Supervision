package com.inei.supervision.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.inei.supervision.R;

public class GpsTrackerService extends Service {
    private static final String TAG = GpsTrackerService.class.getSimpleName();
    LocationManager locationManager;
    double latitude, longitude, altitude;
    boolean isGPSEnable;
    private static final int MINTIME = 1000 * 60 * 2;
    private static final int MINDISTANCE = 1;
    private Notification notification;
    private NotificationManager notificationManager;
    private GpsTracker gpsTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Create service");
        /*
        notification = new Notification.Builder(this)
                .setContentTitle("Servicio activo")
                .setContentText("Hola")
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        */
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        gpsTracker = new GpsTracker();
        LocationListener locationListener = gpsTracker;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, MINDISTANCE, locationListener);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnable) {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Close service");
        notificationManager.cancelAll();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class GpsTracker implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude = location.getAltitude();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            isGPSEnable = true;
        }

        @Override
        public void onProviderDisabled(String s) {
            isGPSEnable = false;
        }
    }
}

