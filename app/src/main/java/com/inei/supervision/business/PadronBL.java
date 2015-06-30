package com.inei.supervision.business;


import android.content.Context;

import com.google.gson.Gson;
import com.inei.supervision.DAO.PadronUbicacionDAO;
import com.inei.supervision.Response.PadronResponse;
import com.inei.supervision.entity.PadronUbicacionEntity;
import com.inei.supervision.request.PadronRequest;

import org.json.JSONObject;

public class PadronBL {
    private static final String TAG = PadronBL.class.getSimpleName();
    private Context context;
    private PadronRequest padronRequest;

    public PadronBL(Context context) {
        this.context = context;
    }

    public void getPadron(){
        padronRequest = new PadronRequest(context);
        JSONObject response = padronRequest.getPadron();
        if (response != null){
            Gson gson = new Gson();
            PadronResponse padronResponse= gson.fromJson(response.toString(), PadronResponse.class);
            PadronUbicacionDAO padronUbicacionDAO = new PadronUbicacionDAO(context);
            padronUbicacionDAO.addPadron(padronResponse.getPadron());
        }
    }
}
