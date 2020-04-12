package com.example.kouveepetshop.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UkuranHewanModel {
    @SerializedName("id_ukuran")
    private String id_ukuran;

    @SerializedName("ukuran")
    private String ukuran;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public UkuranHewanModel(String ukuranHewan, String pic) {
    }

    public UkuranHewanModel(String id_ukuran, String ukuran, String created_at, String updated_at, String pic) {
        this.id_ukuran = id_ukuran;
        this.ukuran = ukuran;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pic = pic;
    }

    public UkuranHewanModel(String id_ukuran, String ukuran, String created_at, String updated_at, String edited_by, String pic) {
        this.id_ukuran = id_ukuran;
        this.ukuran = ukuran;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
        this.pic = pic;
    }

    public String getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(String id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getEdited_by() {
        return edited_by;
    }

    public void setEdited_by(String edited_by) {
        this.edited_by = edited_by;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @NonNull
    @Override
    public String toString() {
        return this.ukuran;
    }
}
