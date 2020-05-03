package com.example.kouveepetshop.result.penjualan_layanan;

import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultPenjualanLayanan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<PenjualanLayananModel> listPenjualanLayanan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PenjualanLayananModel> getListPenjualanLayanan() {
        return listPenjualanLayanan;
    }

    public void setListPenjualanLayanan(List<PenjualanLayananModel> listPenjualanLayanan) {
        this.listPenjualanLayanan = listPenjualanLayanan;
    }
}
