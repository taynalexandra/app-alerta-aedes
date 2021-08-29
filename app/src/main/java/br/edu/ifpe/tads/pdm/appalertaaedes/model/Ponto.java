package br.edu.ifpe.tads.pdm.appalertaaedes.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ponto {
    private final String lat;
    private final String lon;
    private final String type;
    public Ponto() {
        this.lat = null;
        this.lon = null;
        this.type = null;
    }

    public Ponto(String lat, String lon, String type) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getType() {
        return type;
    }
}
