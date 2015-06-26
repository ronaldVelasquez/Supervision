package com.inei.supervision.entity;

public class PadronUbicacionEntity {
    private int id_captura;
    private String fecha_captura;
    private String fecha_registro;
    private String fecha_modificacion;
    private String usuario;

    public PadronUbicacionEntity() {
    }

    public PadronUbicacionEntity(int id_captura, String fecha_captura, String fecha_registro, String fecha_modificacion, String usuario) {
        this.id_captura = id_captura;
        this.fecha_captura = fecha_captura;
        this.fecha_registro = fecha_registro;
        this.fecha_modificacion = fecha_modificacion;
        this.usuario = usuario;
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

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
