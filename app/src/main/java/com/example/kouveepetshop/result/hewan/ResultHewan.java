package com.example.kouveepetshop.result.hewan;

import com.example.kouveepetshop.model.HewanModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultHewan {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<HewanModel> listHewan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HewanModel> getListHewan() {
        return listHewan;
    }

    public void setListHewan(List<HewanModel> listHewan) {
        this.listHewan = listHewan;
    }
}
