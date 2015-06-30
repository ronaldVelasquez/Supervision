package com.inei.supervision.entity;

public class VersionEntity {
    private String nro_version;
    private String usuario;
    private String Fecha_Registro;

    public VersionEntity() {
    }

    public VersionEntity(String nro_version, String usuario, String fecha_Registro) {
        this.nro_version = nro_version;
        this.usuario = usuario;
        Fecha_Registro = fecha_Registro;
    }

    public String getNro_version() {
        return nro_version;
    }

    public void setNro_version(String nro_version) {
        this.nro_version = nro_version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }
}
