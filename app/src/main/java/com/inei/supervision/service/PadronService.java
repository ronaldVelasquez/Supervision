package com.inei.supervision.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.inei.supervision.business.PadronBL;
import com.inei.supervision.business.VersionBL;
import com.inei.supervision.entity.VersionEntity;

public class PadronService extends Service {

    private static final String TAG = PadronService.class.getSimpleName();
    private ReviewPadron reviewPadron;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Create service PadronService");
        reviewPadron = new ReviewPadron(getApplicationContext());
        reviewPadron.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class ReviewPadron extends Thread{
        private VersionBL versionBL;
        private PadronBL padronBL;


        public ReviewPadron(Context context) {
            versionBL = new VersionBL(context);
            padronBL = new PadronBL(context);
        }

        @Override
        public void run() {
            super.run();
            try {
                Log.e(TAG, "Search Version");
                VersionEntity versionEntity = versionBL.getVersionLocal();
                if (versionEntity != null){
                    int nroVersion = Integer.valueOf(versionEntity.getNro_version());
                    versionBL.getVersion(nroVersion);
                } else {
                    Log.e(TAG, "No existe version");
                }
                Log.e(TAG, "End Version");
                Thread.sleep(6 * 60 * 1000);
            } catch (Exception ex){
                ex.printStackTrace();
                Log.e(TAG, "Error Version");
            }
        }
    }
}


