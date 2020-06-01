package com.example.kouveepetshop.result.pengadaan;

import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneDetailPengadaan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private DetailPengadaanModel detailPengadaanModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailPengadaanModel getDetailPengadaanModel() {
        return detailPengadaanModel;
    }

    public void setDetailPengadaanModel(DetailPengadaanModel detailPengadaanModel) {
        this.detailPengadaanModel = detailPengadaanModel;
    }
}
