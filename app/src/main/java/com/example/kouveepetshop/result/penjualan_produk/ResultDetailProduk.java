package com.example.kouveepetshop.result.penjualan_produk;

import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultDetailProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<DetailPenjualanProdukModel> listDetailPenjualanProduk;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DetailPenjualanProdukModel> getListDetailPenjualanProduk() {
        return listDetailPenjualanProduk;
    }

    public void setListDetailPenjualanProduk(List<DetailPenjualanProdukModel> listDetailPenjualanProduk) {
        this.listDetailPenjualanProduk = listDetailPenjualanProduk;
    }
}
