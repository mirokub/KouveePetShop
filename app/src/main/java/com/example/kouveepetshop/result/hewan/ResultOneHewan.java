package com.example.kouveepetshop.result.hewan;

import com.example.kouveepetshop.model.HewanModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneHewan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private HewanModel hewanModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HewanModel getHewanModel() {
        return hewanModel;
    }

    public void setHewanModel(HewanModel hewanModel) {
        this.hewanModel = hewanModel;
    }
}
