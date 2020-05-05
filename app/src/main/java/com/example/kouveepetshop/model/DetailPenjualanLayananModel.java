package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class DetailPenjualanLayananModel {

    @SerializedName("id_detail")
    private String id_detail;

    @SerializedName("nomor_transaksi")
    private String nomor_transaksi;

    @SerializedName("id_layanan")
    private String id_layanan;

    @SerializedName("nama_layanan")
    private String nama_layanan;

    @SerializedName("harga_layanan")
    private String harga_layanan;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("subtotal")
    private String subtotal;


    public DetailPenjualanLayananModel() {
    }

    public DetailPenjualanLayananModel(String nomor_transaksi, String id_layanan, String jumlah, String subtotal) {
        this.nomor_transaksi = nomor_transaksi;
        this.id_layanan = id_layanan;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getNomor_transaksi() {
        return nomor_transaksi;
    }

    public void setNomor_transaksi(String nomor_transaksi) {
        this.nomor_transaksi = nomor_transaksi;
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

    public String getHarga_layanan() {
        return harga_layanan;
    }

    public void setHarga_layanan(String harga_layanan) {
        this.harga_layanan = harga_layanan;
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
