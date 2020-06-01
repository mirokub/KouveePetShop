package com.example.kouveepetshop.result.pengadaan;

import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultPengadaan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<PengadaanModel> listPengadaan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PengadaanModel> getListPengadaan() {
        return listPengadaan;
    }

    public void setListPengadaan(List<PengadaanModel> listPengadaan) {
        this.listPengadaan = listPengadaan;
    }
}
