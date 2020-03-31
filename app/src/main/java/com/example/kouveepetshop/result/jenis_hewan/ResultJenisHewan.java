package com.example.kouveepetshop.result.jenis_hewan;

import com.example.kouveepetshop.model.JenisHewanModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultJenisHewan {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<JenisHewanModel> jenisHewanModels;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<JenisHewanModel> getJenisHewanModels() {
        return jenisHewanModels;
    }

    public void setJenisHewanModels(List<JenisHewanModel> jenisHewanModels) {
        this.jenisHewanModels = jenisHewanModels;
    }
}
