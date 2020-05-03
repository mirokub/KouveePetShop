package com.example.kouveepetshop.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class HewanModel {

    @SerializedName("id_hewan")
    private String id_hewan;

    @SerializedName("nama_hewan")
    private String nama_hewan;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("id_jenis")
    private String id_jenis;

    @SerializedName("ukuran")
    private String ukuran;

    @SerializedName("id_ukuran")
    private String id_ukuran;

    @SerializedName("nama_customer")
    private String nama_customer;

    @SerializedName("id_customer")
    private String id_customer;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public HewanModel() {
    }

    public HewanModel(String id_hewan, String nama_hewan, String tgl_lahir, String jenis, String ukuran, String nama_customer, String created_at, String updated_at, String edited_by) {
        this.id_hewan = id_hewan;
        this.nama_hewan = nama_hewan;
        this.tgl_lahir = tgl_lahir;
        this.jenis = jenis;
        this.ukuran = ukuran;
        this.nama_customer = nama_customer;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
    }

    public HewanModel(String nama_hewan, String tgl_lahir, String id_jenis, String id_ukuran, String id_customer, String pic) {
        this.nama_hewan = nama_hewan;
        this.tgl_lahir = tgl_lahir;
        this.id_jenis = id_jenis;
        this.id_ukuran = id_ukuran;
        this.id_customer = id_customer;
        this.pic = pic;
    }

    public String getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(String id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(String id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
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
        return this.nama_hewan;
    }
}
