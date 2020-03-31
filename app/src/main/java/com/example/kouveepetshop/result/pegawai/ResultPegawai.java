package com.example.kouveepetshop.result.pegawai;

import com.example.kouveepetshop.model.PegawaiModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultPegawai {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private PegawaiModel pegawaiModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PegawaiModel getPegawaiModel() {
        return pegawaiModel;
    }

    public void setPegawaiModel(PegawaiModel pegawaiModel) {
        this.pegawaiModel = pegawaiModel;
    }
}
