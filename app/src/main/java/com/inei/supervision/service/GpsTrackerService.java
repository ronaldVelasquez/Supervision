package com.inei.supervision.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.inei.supervision.DAO.GeoreferenciaDAO;
import com.inei.supervision.R;
import com.inei.supervision.library.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GpsTrackerService extends Service {
    private static final String TAG = GpsTrackerService.class.getSimpleName();
    LocationManager locationManager;
    double latitude, longitude, altitude;

    boolean isGPSEnable;
    private static final int MINTIME = 1000 * 60 * 5;
    private static final int MINDISTANCE = 1;
    private Notification notification;
    private NotificationManager notificationManager;
    private GpsTracker gpsTracker;
    public boolean flag;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Create service");
        flag = false;
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
        gpsTracker = new GpsTracker(getApplicationContext());
        LocationListener locationListener = gpsTracker;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, 1000, locationListener);
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

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class GpsTracker implements LocationListener{

        private GeoreferenciaDAO georeferenciaDAO;
        private SessionManager sessionManager;

        public GpsTracker(Context context) {
            sessionManager = new SessionManager(context);
            georeferenciaDAO = GeoreferenciaDAO.getInstance(context);
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = sdf.format(date);

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                HashMap<String, String> user = sessionManager.getUserDetails();
                String dni = user.get(SessionManager.KEY_DNI);
                georeferenciaDAO.addGeoreferencia(latitude, longitude, dni, dateString);
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

