package com.example.kouveepetshop.result.produk;

import com.example.kouveepetshop.model.ProdukModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<ProdukModel> listProduk;

    public List<ProdukModel> getListProduk() {
        return listProduk;
    }

    public void setListProduk(List<ProdukModel> listProduk) {
        this.listProduk = listProduk;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
