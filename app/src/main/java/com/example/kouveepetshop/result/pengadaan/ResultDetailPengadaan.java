package com.example.kouveepetshop.result.pengadaan;

import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultDetailPengadaan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<DetailPengadaanModel> listDetailPengadaan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DetailPengadaanModel> getListDetailPengadaan() {
        return listDetailPengadaan;
    }

    public void setListDetailPengadaan(List<DetailPengadaanModel> listDetailPengadaan) {
        this.listDetailPengadaan = listDetailPengadaan;
    }
}
