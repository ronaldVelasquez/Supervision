package com.inei.supervision.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.inei.supervision.activity.MainActivity;
import com.inei.supervision.business.PadronBL;
import com.inei.supervision.business.VersionBL;

public class PadronAsynTask extends AsyncTask {

    private static final String TAG = PadronAsynTask.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;
    private VersionBL versionBL;
    private PadronBL padronBL;

    public PadronAsynTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context, "Obteniendo datos y buscando padrón");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.v(TAG, "Start PadronAsync");
        versionBL = new VersionBL(context);
       if( versionBL.getVersion()){
           padronBL = new PadronBL(context);
           padronBL.getPadron();
           Intent intent = new Intent(this.context.getApplicationContext(), MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
       } else {
           Log.v(TAG, "Error en momento de escribir la version");
       }
        Log.v(TAG, "End PadronAsync");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        progressDialog.cancel();
    }
}
