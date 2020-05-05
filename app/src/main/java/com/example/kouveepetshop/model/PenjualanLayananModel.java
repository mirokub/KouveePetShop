package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class PenjualanLayananModel {

    @SerializedName("id")
    private String id;

    @SerializedName("nomor_transaksi")
    private String nomor_transaksi;

    @SerializedName("id_hewan")
    private String id_hewan;

    @SerializedName("nama_hewan")
    private String nama_hewan;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("ukuran")
    private String ukuran;

    @SerializedName("id_customer")
    private String id_customer;

    @SerializedName("nama_customer")
    private String nama_customer;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("tgl_penjualan")
    private String tgl_penjualan;

    @SerializedName("status_layanan")
    private String status_layanan;

    @SerializedName("total")
    private String total;

    @SerializedName("status_pembayaran")
    private String status_pembayaran;

    @SerializedName("customer_service")
    private String customer_service;

    @SerializedName("id_cs")
    private String id_cs;

    public PenjualanLayananModel() {
    }

    public PenjualanLayananModel(String id_hewan, String id_cs) {
        this.id_hewan = id_hewan;
        this.id_cs = id_cs;
    }

    public PenjualanLayananModel(String id_cs) {
        this.id_cs = id_cs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor_transaksi() {
        return nomor_transaksi;
    }

    public void setNomor_transaksi(String nomor_transaksi) {
        this.nomor_transaksi = nomor_transaksi;
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
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

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getTgl_penjualan() {
        return tgl_penjualan;
    }

    public void setTgl_penjualan(String tgl_penjualan) {
        this.tgl_penjualan = tgl_penjualan;
    }

    public String getStatus_layanan() {
        return status_layanan;
    }

    public void setStatus_layanan(String status_layanan) {
        this.status_layanan = status_layanan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public void setCustomer_service(String customer_service) {
        this.customer_service = customer_service;
    }

    public String getId_cs() {
        return id_cs;
    }

    public void setId_cs(String id_cs) {
        this.id_cs = id_cs;
    }
}
