package com.example.kouveepetshop.result.pengadaan;

import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

public class ResultOnePengadaan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private PengadaanModel pengadaanModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PengadaanModel getPengadaanModel() {
        return pengadaanModel;
    }

    public void setPengadaanModel(PengadaanModel pengadaanModel) {
        this.pengadaanModel = pengadaanModel;
    }
}
