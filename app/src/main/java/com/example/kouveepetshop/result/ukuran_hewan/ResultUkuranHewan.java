package com.example.kouveepetshop.result.ukuran_hewan;

import com.example.kouveepetshop.model.UkuranHewanModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultUkuranHewan {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<UkuranHewanModel> ukuranHewanModels;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UkuranHewanModel> getUkuranHewanModels() {
        return ukuranHewanModels;
    }

    public void setUkuranHewanModels(List<UkuranHewanModel> ukuranHewanModels) {
        this.ukuranHewanModels = ukuranHewanModels;
    }
}
