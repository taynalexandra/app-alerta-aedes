package br.edu.ifpe.tads.pdm.appalertaaedes.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Case {
    private int id;
    private String type;
    private List<String> symptoms;
    private LatLng latLng;

    public Case(String type, List<String> symptoms, LatLng latLng) {
        this.type = type;
        this.symptoms = symptoms;
        this.latLng = latLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
