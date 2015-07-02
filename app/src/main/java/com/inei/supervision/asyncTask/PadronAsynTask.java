package com.inei.supervision.asyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.gson.Gson;
import com.inei.supervision.DAO.PadronUbicacionDAO;
import com.inei.supervision.DAO.VersionDAO;
import com.inei.supervision.Response.PadronResponse;
import com.inei.supervision.activity.MainActivity;
import com.inei.supervision.business.VersionBL;
import com.inei.supervision.entity.VersionEntity;
import com.inei.supervision.library.SessionManager;
import com.inei.supervision.utils.ConstantsUtil;

import org.json.JSONObject;

public class PadronAsynTask extends AsyncTask {

    private static final String TAG = PadronAsynTask.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;
    private VersionDAO versionDAO;
    private VersionBL versionBL;
    private PadronUbicacionDAO padronUbicacionDAO;
    private String dni;
    private String nro_serie;
    private int id;
    private SessionManager sessionManager;

    public PadronAsynTask(Context context, String dni, String nro_serie, int id) {
        super();
        this.context = context;
        versionDAO = VersionDAO.getInstance(context);
        padronUbicacionDAO = PadronUbicacionDAO.getInstance(context);
        versionBL = new VersionBL(context);
        this.dni = dni;
        this.nro_serie = nro_serie;
        this.id = id;
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
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_VERSION, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                VersionEntity versionEntity = versionBL.getVersion(response.toString());
                VersionEntity versionEntityDAO = versionDAO.getVersion();
                if(Integer.valueOf(versionEntity.getNro_version()) > Integer.valueOf(versionEntityDAO.getNro_version())){
                    versionDAO.addVersion(versionEntity);

                    RequestQueue queue = Volley.newRequestQueue(context);
                    JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_PADRON, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            sessionManager = new SessionManager(PadronAsynTask.this.context.getApplicationContext());
                            sessionManager.createLoginSession(dni, nro_serie, id);
                            Gson gson = new Gson();
                            PadronResponse padronResponse = gson.fromJson(response.toString(), PadronResponse.class);
                            Log.v(TAG, padronResponse.getPadron_ubicacion().get(0).getFecha_captura());
                            padronUbicacionDAO.addPadron(padronResponse.getPadron_ubicacion());

                            Intent intent = new Intent(PadronAsynTask.this.context.getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.v(TAG, volleyError.toString());
                            Toast.makeText(PadronAsynTask.this.context.getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(jsonArrayRequest);
                } else if(Integer.valueOf(versionEntity.getNro_version()) == Integer.valueOf(versionEntityDAO.getNro_version())){
                    sessionManager = new SessionManager(PadronAsynTask.this.context.getApplicationContext());
                    sessionManager.createLoginSession(dni, nro_serie, id);
                    Intent intent = new Intent(PadronAsynTask.this.context.getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(PadronAsynTask.this.context.getApplicationContext(), "Error de la nube", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, volleyError.toString());
                Toast.makeText(PadronAsynTask.this.context.getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        progressDialog.cancel();
    }
}
