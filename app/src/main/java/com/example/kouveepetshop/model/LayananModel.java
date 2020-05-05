package com.example.kouveepetshop.model;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class LayananModel {
    @SerializedName("id_layanan")
    private String id_layanan;

    @SerializedName("nama_layanan")
    private String nama_layanan;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("id_jenis")
    private String id_jenis;

    @SerializedName("ukuran")
    private String ukuran;

    @SerializedName("id_ukuran")
    private String id_ukuran;

    @SerializedName("harga")
    private String harga;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public LayananModel() {
    }

    public LayananModel(String id_layanan, String nama_layanan, String jenis,
                        String id_jenis, String ukuran, String id_ukuran, String harga,
                        String created_at, String updated_at, String edited_by, String pic) {
        this.id_layanan = id_layanan;
        this.nama_layanan = nama_layanan;
        this.jenis = jenis;
        this.id_jenis = id_jenis;
        this.ukuran = ukuran;
        this.id_ukuran = id_ukuran;
        this.harga = harga;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
        this.pic = pic;
    }

    public LayananModel(String nama_layanan, String id_jenis,
                        String id_ukuran, String harga, String pic) {
        this.nama_layanan = nama_layanan;
        this.id_jenis = id_jenis;
        this.id_ukuran = id_ukuran;
        this.harga = harga;
        this.pic = pic;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
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
        return this.nama_layanan + " " + this.jenis + " " + this.ukuran;
    }
}
