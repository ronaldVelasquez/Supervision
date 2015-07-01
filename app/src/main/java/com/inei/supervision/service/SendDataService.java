package com.inei.supervision.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inei.supervision.DAO.GeoreferenciaDAO;
import com.inei.supervision.entity.GeoreferenciaEntity;

import java.util.ArrayList;

public class SendDataService extends Service {
    private static final String TAG = SendDataService.class.getSimpleName();
    private GeoreferenciaDAO georeferenciaDAO;
    public SendDataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        georeferenciaDAO = new GeoreferenciaDAO(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class DataSend extends Thread{
        private ArrayList<GeoreferenciaEntity> arrayGeoreferencia;
        public DataSend() {
        }

        @Override
        public void run() {
            SendDataService sendDataService = SendDataService.this;
            Log.e(TAG, "UpdaterThread running");
            try
            {
                arrayGeoreferencia = georeferenciaDAO.getData();
                if(arrayGeoreferencia != null){
                    Gson gson = new Gson();
                    String jsonList = gson.toJson(arrayGeoreferencia);

                }
                Thread.sleep( 5*60*1000 );
            }
            catch (InterruptedException e)
            {
                Log.e(TAG, "UpdaterThread error");
            }
        }
    }
}
