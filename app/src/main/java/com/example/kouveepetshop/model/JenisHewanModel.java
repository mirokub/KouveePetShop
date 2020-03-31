package com.example.kouveepetshop.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class JenisHewanModel {
    @SerializedName("id_jenis")
    private String id_jenis;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public JenisHewanModel() {
    }

    public JenisHewanModel(String id_jenis, String jenis, String created_at, String updated_at, String edited_by, String pic) {
        this.id_jenis = id_jenis;
        this.jenis = jenis;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
        this.pic = pic;
    }

    public JenisHewanModel(String id_jenis, String jenis, String created_at, String updated_at, String edited_by) {
        this.id_jenis = id_jenis;
        this.jenis = jenis;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
    }

    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
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
        return this.jenis;
    }
}
