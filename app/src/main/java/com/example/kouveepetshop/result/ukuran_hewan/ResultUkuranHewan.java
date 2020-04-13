package com.example.kouveepetshop.result.ukuran_hewan;

import com.example.kouveepetshop.model.UkuranHewanModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultUkuranHewan {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<UkuranHewanModel> listUkuranHewan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UkuranHewanModel> getListUkuranHewan() {
        return listUkuranHewan;
    }

    public void setUkuranHewanModels(List<UkuranHewanModel> ukuranHewanModels) {
        this.listUkuranHewan = ukuranHewanModels;
    }
}
