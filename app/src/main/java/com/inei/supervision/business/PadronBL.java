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
import com.inei.supervision.Response.PadronResponse;
import com.inei.supervision.request.PadronRequest;
import com.inei.supervision.utils.ConstantsUtil;

import org.json.JSONObject;

public class PadronBL {
    private static final String TAG = PadronBL.class.getSimpleName();
    private Context context;
    private PadronRequest padronRequest;
    private PadronUbicacionDAO padronUbicacionDAO;

    public PadronBL(Context context) {
        this.context = context;
        padronUbicacionDAO = PadronUbicacionDAO.getInstance(context);
    }

    public void getPadron(){
        padronRequest = new PadronRequest(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_PADRON, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                if (response.toString() != null){
                    Gson gson = new Gson();
                    PadronResponse padronResponse= gson.fromJson(response.toString(), PadronResponse.class);
                    padronUbicacionDAO.addPadron(padronResponse.getPadron_ubicacion());
                }
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
