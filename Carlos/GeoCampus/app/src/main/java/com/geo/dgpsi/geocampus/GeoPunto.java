package com.geo.dgpsi.geocampus;

import com.google.android.gms.internal.la;

/**
 * Created by Giraldillo on 22/01/2015.
 */
public class GeoPunto {
    private Float latitud, longitud;
    private String etiqueta, comentario, uri;
    private Integer id_global, id_local;
    public boolean eliminado = false;

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getId_global() {
        return id_global;
    }

    public void setId_global(Integer id_global) {
        this.id_global = id_global;
    }

    public Integer getId_local() {
        return id_local;
    }

    public void setId_local(Integer id_local) {
        this.id_local = id_local;
    }

    public GeoPunto (Integer local, Integer global,Float lon, Float lat, String tag, String uri, String comm){
        this.latitud = lat;
        this.longitud =  lon;
        this.etiqueta = tag;
        this.comentario = comm;
        this.uri = uri;
        this.id_local = local;
        this.id_global = global;

    }

    public String getUri(){
        return this.uri;
    }
}
