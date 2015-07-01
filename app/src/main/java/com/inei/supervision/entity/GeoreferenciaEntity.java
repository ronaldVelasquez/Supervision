package com.inei.supervision.entity;

public class GeoreferenciaEntity {
    private int id_captura;
    private int id;
    private double latitud;
    private double longitud;
    private double altitud;
    private String fecha_registro;
    private String fecha_modificacion;
    private String usuario;
    private int estado;

    public GeoreferenciaEntity(int id_captura, int id, double latitud, double longitud, double altitud, String fecha_registro, String fecha_modificacion, String usuario, int estado) {
        this.id_captura = id_captura;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.fecha_registro = fecha_registro;
        this.fecha_modificacion = fecha_modificacion;
        this.usuario = usuario;
        this.estado = estado;
    }

    public GeoreferenciaEntity() {
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_captura() {
        return id_captura;
    }

    public void setId_captura(int id_captura) {
        this.id_captura = id_captura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
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
