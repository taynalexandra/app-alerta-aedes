package br.edu.ifpe.tads.pdm.appalertaaedes.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Case {

    private String type;
    private List<String> symptoms;
    private String lat;
    private String lon;

    public Case() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
