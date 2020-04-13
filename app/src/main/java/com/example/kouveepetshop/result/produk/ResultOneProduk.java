package com.example.kouveepetshop.result.produk;

import com.example.kouveepetshop.model.ProdukModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneProduk {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private ProdukModel produkModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProdukModel getProdukModel() {
        return produkModel;
    }

    public void setProdukModel(ProdukModel produkModel) {
        this.produkModel = produkModel;
    }

}
