package com.example.kouveepetshop.result.penjualan_produk;

import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.google.gson.annotations.SerializedName;

public class ResultOnePenjualanProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private PenjualanProdukModel penjualanProdukModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PenjualanProdukModel getPenjualanProdukModel() {
        return penjualanProdukModel;
    }

    public void setPenjualanProdukModel(PenjualanProdukModel penjualanProdukModel) {
        this.penjualanProdukModel = penjualanProdukModel;
    }
}
