package com.example.kouveepetshop.result.penjualan_produk;

import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultPenjualanProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<PenjualanProdukModel> listPenjualanProduk;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PenjualanProdukModel> getListPenjualanProduk() {
        return listPenjualanProduk;
    }

    public void setListPenjualanProduk(List<PenjualanProdukModel> listPenjualanProduk) {
        this.listPenjualanProduk = listPenjualanProduk;
    }
}
