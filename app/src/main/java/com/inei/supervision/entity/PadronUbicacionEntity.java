package com.inei.supervision.entity;

public class PadronUbicacionEntity {
    private int id_captura;
    private String fecha_captura;

    public PadronUbicacionEntity() {

    }

    public PadronUbicacionEntity(int id_captura, String fecha_captura) {
        this.id_captura = id_captura;
        this.fecha_captura = fecha_captura;
    }

    public int getId_captura() {
        return id_captura;
    }

    public void setId_captura(int id_captura) {
        this.id_captura = id_captura;
    }

    public String getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(String fecha_captura) {
        this.fecha_captura = fecha_captura;
    }
}