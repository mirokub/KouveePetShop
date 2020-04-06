package com.example.kouveepetshop.result.layanan;

import com.example.kouveepetshop.model.LayananModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneLayanan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private LayananModel layananModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LayananModel getLayananModel() {
        return layananModel;
    }

    public void setLayananModel(LayananModel layananModel) {
        this.layananModel = layananModel;
    }
}
