package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class DetailPenjualanProdukModel {

    @SerializedName("id_detail")
    private String id_detail;

    @SerializedName("nomor_transaksi")
    private String nomor_transaksi;

    @SerializedName("id_produk")
    private String id_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("harga_produk")
    private String harga_produk;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("subtotal")
    private String subtotal;


    public DetailPenjualanProdukModel() {
    }

    public DetailPenjualanProdukModel(String nomor_transaksi, String id_produk, String jumlah, String subtotal) {

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

    public String getNomor_transaksi() {
        return nomor_transaksi;
    }

    public void setNomor_transaksi(String nomor_transaksi) {
        this.nomor_transaksi = nomor_transaksi;
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

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
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
