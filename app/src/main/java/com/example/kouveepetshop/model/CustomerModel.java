package com.example.kouveepetshop.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {

    @SerializedName("id_customer")
    private String id_customer;

    @SerializedName("nama_customer")
    private String nama_customer;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public CustomerModel() {
    }

    public CustomerModel(String nama_customer, String alamat, String tgl_lahir, String no_telp, String created_at, String updated_at, String edited_by, String pic) {
        this.nama_customer = nama_customer;
        this.alamat = alamat;
        this.tgl_lahir = tgl_lahir;
        this.no_telp = no_telp;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
        this.pic = pic;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
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
        return this.nama_customer;
    }
}
