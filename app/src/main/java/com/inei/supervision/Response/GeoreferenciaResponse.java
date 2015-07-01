package com.inei.supervision.Response;

import com.inei.supervision.entity.GeoreferenciaEntity;

import java.util.ArrayList;

public class GeoreferenciaResponse {
    private ArrayList<GeoreferenciaEntity> data;

    public GeoreferenciaResponse(ArrayList<GeoreferenciaEntity> data) {
        this.data = data;
    }

    public ArrayList<GeoreferenciaEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GeoreferenciaEntity> data) {
        this.data = data;
    }
}
