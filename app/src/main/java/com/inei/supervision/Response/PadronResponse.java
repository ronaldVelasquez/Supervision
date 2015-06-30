package com.inei.supervision.Response;


import com.inei.supervision.entity.PadronUbicacionEntity;

import java.util.ArrayList;

public class PadronResponse {
    private ArrayList<PadronUbicacionEntity> padron;

    public PadronResponse(ArrayList<PadronUbicacionEntity> padron) {
        this.padron = padron;
    }

    public ArrayList<PadronUbicacionEntity> getPadron() {
        return padron;
    }

    public void setPadron(ArrayList<PadronUbicacionEntity> padron) {
        this.padron = padron;
    }
}
