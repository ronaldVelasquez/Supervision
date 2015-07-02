package com.inei.supervision.request;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inei.supervision.DAO.GeoreferenciaDAO;
import com.inei.supervision.Response.GeoreferenciaResponse;
import com.inei.supervision.entity.GeoreferenciaEntity;
import com.inei.supervision.utils.ConstantsUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GeorefenciaResquest {
    private static final String TAG = GeorefenciaResquest.class.getSimpleName();
    private Context context;
    private GeoreferenciaDAO georeferenciaDAO;

    public GeorefenciaResquest(Context context) {
        this.context = context;
        georeferenciaDAO = GeoreferenciaDAO.getInstance(context);
    }

    public void sendData(String jsonList) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);

        HashMap<String, JSONArray> parameters = new HashMap<String, JSONArray>();
        parameters.put("data", new JSONArray("[" + jsonList + "]"));

        Log.v(TAG, "JSON: " + new JSONObject(parameters));
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ConstantsUtil.URL_GEOREFERENCIA, new JSONObject(parameters),new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.v(TAG, jsonArray.toString());
                Gson gson = new Gson();
                try {
                    GeoreferenciaResponse georeferenciaResponse = gson.fromJson(jsonArray.get(0).toString(), GeoreferenciaResponse.class);
                    georeferenciaDAO.updateGeoreferencia(georeferenciaResponse.getData());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, volleyError.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(jsonArrayRequest);
    }


}
