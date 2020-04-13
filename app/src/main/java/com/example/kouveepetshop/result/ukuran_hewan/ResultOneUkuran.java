package com.example.kouveepetshop.result.ukuran_hewan;

import com.example.kouveepetshop.model.UkuranHewanModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneUkuran {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private UkuranHewanModel ukuranHewanModel;

    public UkuranHewanModel getUkuranHewanModel() {
        return ukuranHewanModel;
    }

    public void setUkuranHewanModel(UkuranHewanModel ukuranHewanModel) {
        this.ukuranHewanModel = ukuranHewanModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
