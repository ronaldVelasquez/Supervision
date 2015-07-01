package com.inei.supervision.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.inei.supervision.DAO.GeoreferenciaDAO;
import com.inei.supervision.entity.GeoreferenciaEntity;
import com.inei.supervision.utils.ConstantsUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeorefenciaResquest {
    private static final String TAG = GeorefenciaResquest.class.getSimpleName();
    private Context context;
    private GeoreferenciaDAO georeferenciaDAO;

    public GeorefenciaResquest(Context context) {
        this.context = context;
        georeferenciaDAO = GeoreferenciaDAO.getInstance(context);
    }

    public void sendData(String jsonList){
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ConstantsUtil.URL_GEOREFERENCIA, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.v(TAG, jsonArray.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

}
