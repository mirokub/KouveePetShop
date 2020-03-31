package com.example.kouveepetshop.result.layanan;

import com.example.kouveepetshop.model.LayananModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultLayanan {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<LayananModel> listLayanan;

    public List<LayananModel> getListLayanan() {
        return listLayanan;
    }

    public void setListLayanan(List<LayananModel> listLayanan) {
        this.listLayanan = listLayanan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
