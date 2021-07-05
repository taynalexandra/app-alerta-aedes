package br.edu.ifpe.tads.pdm.appalertaaedes.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Case {
    private String type;
    private List<String> symptoms;
    private LatLng latLng;

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

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
