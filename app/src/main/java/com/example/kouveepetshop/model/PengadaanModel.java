package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class PengadaanModel {

    @SerializedName("id")
    private String id;

    @SerializedName("nomor_pengadaan")
    private String nomor_pengadaan;

    @SerializedName("id_supplier")
    private String id_supplier;

    @SerializedName("nama_supplier")
    private String nama_supplier;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("tgl_pengadaan")
    private String tgl_pengadaan;

    @SerializedName("status_cetak_surat")
    private String status_cetak_surat;

    @SerializedName("status_kedatangan")
    private String status_kedatangan;

    @SerializedName("total")
    private String total;

    @SerializedName("owner")
    private String owner;

    @SerializedName("pic")
    private String pic;

    public PengadaanModel() {
    }

    public PengadaanModel(String id_supplier, String pic) {
        this.id_supplier = id_supplier;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor_pengadaan() {
        return nomor_pengadaan;
    }

    public void setNomor_pengadaan(String nomor_pengadaan) {
        this.nomor_pengadaan = nomor_pengadaan;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_pengadaan() {
        return tgl_pengadaan;
    }

    public void setTgl_pengadaan(String tgl_pengadaan) {
        this.tgl_pengadaan = tgl_pengadaan;
    }

    public String getStatus_cetak_surat() {
        return status_cetak_surat;
    }

    public void setStatus_cetak_surat(String status_cetak_surat) {
        this.status_cetak_surat = status_cetak_surat;
    }

    public String getStatus_kedatangan() {
        return status_kedatangan;
    }

    public void setStatus_kedatangan(String status_kedatangan) {
        this.status_kedatangan = status_kedatangan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
