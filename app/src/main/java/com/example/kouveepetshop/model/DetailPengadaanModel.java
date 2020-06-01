package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class DetailPengadaanModel {

    @SerializedName("id_detail")
    private String id_detail;

    @SerializedName("nomor_pengadaan")
    private String nomor_pengadaan;

    @SerializedName("id_produk")
    private String id_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("harga_beli")
    private String harga_beli;

    @SerializedName("satuan")
    private String satuan;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("subtotal")
    private String subtotal;

    public DetailPengadaanModel() {
    }

    public DetailPengadaanModel(String nomor_pengadaan, String id_produk, String jumlah, String subtotal) {
        this.nomor_pengadaan = nomor_pengadaan;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getNomor_pengadaan() {
        return nomor_pengadaan;
    }

    public void setNomor_pengadaan(String nomor_pengadaan) {
        this.nomor_pengadaan = nomor_pengadaan;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(String harga_beli) {
        this.harga_beli = harga_beli;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
