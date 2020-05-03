package com.example.kouveepetshop.result.penjualan_layanan;

import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.google.gson.annotations.SerializedName;

public class ResultOnePenjualanLayanan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private PenjualanLayananModel penjualanLayananModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PenjualanLayananModel getPenjualanLayananModel() {
        return penjualanLayananModel;
    }

    public void setPenjualanLayananModel(PenjualanLayananModel penjualanLayananModel) {
        this.penjualanLayananModel = penjualanLayananModel;
    }
}
