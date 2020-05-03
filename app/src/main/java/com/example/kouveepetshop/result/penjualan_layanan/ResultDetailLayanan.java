package com.example.kouveepetshop.result.penjualan_layanan;

import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultDetailLayanan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<DetailPenjualanLayananModel> listDetailPenjualanLayanan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DetailPenjualanLayananModel> getListDetailPenjualanLayanan() {
        return listDetailPenjualanLayanan;
    }

    public void setListDetailPenjualanLayanan(List<DetailPenjualanLayananModel> listDetailPenjualanLayanan) {
        this.listDetailPenjualanLayanan = listDetailPenjualanLayanan;
    }
}
