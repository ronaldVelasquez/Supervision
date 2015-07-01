package com.inei.supervision.Response;


import com.inei.supervision.entity.PadronUbicacionEntity;

import java.util.ArrayList;

public class PadronResponse {
    private ArrayList<PadronUbicacionEntity> padron_ubicacion;

    public PadronResponse(ArrayList<PadronUbicacionEntity> padron) {
        this.padron_ubicacion = padron;
    }

    public ArrayList<PadronUbicacionEntity> getPadron_ubicacion() {
        return padron_ubicacion;
    }

    public void setPadron_ubicacion(ArrayList<PadronUbicacionEntity> padron_ubicacion) {
        this.padron_ubicacion = padron_ubicacion;
    }
}
