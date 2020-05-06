package com.example.kouveepetshop.result.penjualan_produk;

import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneDetailProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private DetailPenjualanProdukModel detailPenjualanProdukModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailPenjualanProdukModel getDetailPenjualanProdukModel() {
        return detailPenjualanProdukModel;
    }

    public void setDetailPenjualanProdukModel(DetailPenjualanProdukModel detailPenjualanProdukModel) {
        this.detailPenjualanProdukModel = detailPenjualanProdukModel;
    }
}
