package com.example.kouveepetshop.result.penjualan_layanan;

import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneDetailLayanan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private DetailPenjualanLayananModel detailPenjualanLayananModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailPenjualanLayananModel getDetailPenjualanLayananModel() {
        return detailPenjualanLayananModel;
    }

    public void setDetailPenjualanLayananModel(DetailPenjualanLayananModel detailPenjualanLayananModel) {
        this.detailPenjualanLayananModel = detailPenjualanLayananModel;
    }
}
