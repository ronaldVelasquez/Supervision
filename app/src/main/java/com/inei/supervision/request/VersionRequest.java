package com.inei.supervision.request;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inei.supervision.utils.ConstantsUtil;
import org.json.JSONObject;

public class VersionRequest {

    private static final String TAG = VersionRequest.class.getSimpleName();
    private Context context;
    public static String jsonResponse;

    public VersionRequest(Context context) {
        this.context = context;
    }

    public void getVersion(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_VERSION, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                VersionRequest.jsonResponse =  response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, volleyError.toString());
                VersionRequest.jsonResponse =  null;
            }
        });
        queue.add(jsonArrayRequest);
        Log.v(TAG, "version: " + VersionRequest.jsonResponse);
    }
}
