package com.example.kouveepetshop.result.jenis_hewan;

import com.example.kouveepetshop.model.JenisHewanModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneJenis {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private JenisHewanModel jenisHewanModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JenisHewanModel getJenisHewanModel() {
        return jenisHewanModel;
    }

    public void setJenisHewanModel(JenisHewanModel jenisHewanModel) {
        this.jenisHewanModel = jenisHewanModel;
    }
}
