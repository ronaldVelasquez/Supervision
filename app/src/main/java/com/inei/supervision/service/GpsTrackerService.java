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
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.inei.supervision.DAO.GeoreferenciaDAO;
import com.inei.supervision.Response.GeoreferenciaResponse;
import com.inei.supervision.entity.GeoreferenciaEntity;
import com.inei.supervision.library.SessionManager;
import com.inei.supervision.request.GeorefenciaResquest;
import org.json.JSONException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GpsTrackerService extends Service {
    private static final String TAG = GpsTrackerService.class.getSimpleName();
    LocationManager locationManager;
    double latitude, longitude, altitude;

    boolean isGPSEnable;
    private static final int MINTIME = 1000 * 20 ;
    private static final int MINDISTANCE = 1;
    private GpsTracker gpsTracker;
    public boolean flag;
    private GeoreferenciaDAO georeferenciaDAO;
    private GeorefenciaResquest georefenciaResquest;
    private boolean runFlag = false;
    DataSend dataSend;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Create service");
        flag = false;

        dataSend = new DataSend();
        /*
        notification = new Notification.Builder(this)
                .setContentTitle("Servicio activo")
                .setContentText("Hola")
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        */
        georefenciaResquest = new GeorefenciaResquest(this);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        gpsTracker = new GpsTracker(getApplicationContext());
        LocationListener locationListener = gpsTracker;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, 0, locationListener);
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand UpdaterService" );

        if (!runFlag)
        {
            runFlag = true;
            dataSend.start();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class GpsTracker implements LocationListener{

        private SessionManager sessionManager;
        private Context context;
        public GpsTracker(Context context) {
            this.context = context;
            sessionManager = new SessionManager(context);
            georeferenciaDAO = GeoreferenciaDAO.getInstance(getApplicationContext());
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.v(TAG, "Location found");

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = sdf.format(date);

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(context, "Latitud: " + latitude + ", longitud: " + longitude, Toast.LENGTH_SHORT).show();

                HashMap<String, Object> user = sessionManager.getUserDetails();
                String dni = String.valueOf(user.get(SessionManager.KEY_DNI));
                int id = (Integer) user.get(SessionManager.KEY_ID);
                georeferenciaDAO.addGeoreferencia(latitude, longitude, dni, dateString, id);
                /*
                if(flag){
                    ArrayList<GeoreferenciaEntity>arrayGeoreferencia = georeferenciaDAO.getData();
                    if(arrayGeoreferencia != null){
                        GeoreferenciaResponse georeferenciaResponse = new GeoreferenciaResponse(arrayGeoreferencia);
                        Gson gson = new Gson();
                        String jsonList = gson.toJson(georeferenciaResponse);
                        Log.v(TAG, "JSON: " + jsonList);
                        try{
                            georefenciaResquest.sendData(jsonList);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }
                */
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

    public class DataSend extends Thread{

        public DataSend() {
            georeferenciaDAO = GeoreferenciaDAO.getInstance(getApplicationContext());
        }

        @Override
        public void run() {
            GpsTrackerService gpsTrackerService = GpsTrackerService.this;
            while( gpsTrackerService.runFlag ){
                try
                {
                    Log.e(TAG, "Send Data running");
                    ArrayList<GeoreferenciaEntity>arrayGeoreferencia = new ArrayList<>();
                    arrayGeoreferencia = georeferenciaDAO.getData();
                    if(arrayGeoreferencia != null){
                        GeoreferenciaResponse georeferenciaResponse = new GeoreferenciaResponse(arrayGeoreferencia);
                        Gson gson = new Gson();
                        String jsonList = gson.toJson(georeferenciaResponse);
                        Log.v(TAG, "JSON: " + jsonList);
                        try{
                            georefenciaResquest.sendData(jsonList);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    Thread.sleep( 1 * 60 * 1000 );
                }
                catch (InterruptedException e)
                {
                    Log.e(TAG, "UpdaterThread error");
                }
            }

        }
    }
}

