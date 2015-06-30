package com.inei.supervision.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inei.supervision.utils.ConstantsUtil;
import org.json.JSONObject;

public class PadronRequest {
    public static final String TAG = PadronRequest.class.getSimpleName();
    private Context context;
    private JSONObject jsonObject;

    public PadronRequest(Context context) {
        this.context = context;
    }

    public JSONObject getPadron(){
        Log.v(TAG, "get padron");
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, ConstantsUtil.URL_PADRON, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                jsonObject = response;
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(TAG, volleyError.toString());
                jsonObject = null;
            }
        });
        queue.add(jsonArrayRequest);
        return jsonObject;
    }
}
