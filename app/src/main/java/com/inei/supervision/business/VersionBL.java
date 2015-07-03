package com.inei.supervision.business;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inei.supervision.DAO.PadronUbicacionDAO;
import com.inei.supervision.DAO.VersionDAO;
import com.inei.supervision.Response.PadronResponse;
import com.inei.supervision.Response.VersionResponse;
import com.inei.supervision.entity.VersionEntity;
import com.inei.supervision.request.VersionRequest;
import com.inei.supervision.utils.ConstantsUtil;
import org.json.JSONObject;

public class VersionBL {
    private static final String TAG = VersionBL.class.getSimpleName();
    private Context context;
    private VersionRequest versionRequest;
    private VersionEntity versionEntity, version;
    private VersionDAO versionDAO;
    public static boolean flag;
    private PadronUbicacionDAO padronUbicacionDAO;

    public VersionBL(Context context) {
        this.context = context;
        versionDAO = VersionDAO.getInstance(context);
        padronUbicacionDAO = PadronUbicacionDAO.getInstance(context);
    }

    public void getVersion(final int nroVersion){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_VERSION, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                version = getVersion(response.toString());
                Log.e("Version", String.valueOf(nroVersion) + ", " + version.getNro_version());
                if(Integer.valueOf(versionEntity.getNro_version()) < Integer.valueOf(version.getNro_version())){
                    versionDAO.addVersion(version);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_PADRON, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG, response.toString());
                            Gson gson = new Gson();
                            PadronResponse padronResponse = gson.fromJson(response.toString(), PadronResponse.class);
                            padronUbicacionDAO.addPadron(padronResponse.getPadron_ubicacion());
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.v(TAG, volleyError.toString());
                        }
                    });
                    queue.add(jsonArrayRequest);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, volleyError.toString());
                flag = false;
            }
        });
        queue.add(jsonArrayRequest);
    }

    //Parse with gson
    public VersionEntity getVersion(String response){
        Gson gson = new Gson();
        Log.v("Version: ", response);
        VersionResponse versionResponse = gson.fromJson(response, VersionResponse.class);
        VersionEntity version = versionResponse.getVersion().get(0);
        return version;
    }

    // get version from local database
    public VersionEntity getVersionLocal(){
        VersionEntity versionEntity = versionDAO.getVersion();
        return versionEntity;
    }
}
