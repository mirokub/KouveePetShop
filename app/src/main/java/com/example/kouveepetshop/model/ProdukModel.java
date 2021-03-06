package com.example.kouveepetshop.model;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class ProdukModel {

    @SerializedName("id_produk")
    private String id_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("satuan")
    private String satuan;

    @SerializedName("harga_jual")
    private String harga_jual;

    @SerializedName("harga_beli")
    private String harga_beli;

    @SerializedName("stok")
    private String stok;

    @SerializedName("stok_minimum")
    private String stok_minimum;

    @SerializedName("gambar")
    private String gambar;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("nama_pegawai")
    private String edited_by;

    @SerializedName("pic")
    private String pic;

    public ProdukModel() {
    }

    public ProdukModel(String id_produk, String nama_produk, String satuan, String harga_jual, String harga_beli, String stok, String stok_minimum, String gambar, String created_at, String updated_at, String edited_by, String pic) {
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.satuan = satuan;
        this.harga_jual = harga_jual;
        this.harga_beli = harga_beli;
        this.stok = stok;
        this.stok_minimum = stok_minimum;
        this.gambar = gambar;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
        this.pic = pic;
    }

    public ProdukModel(String nama_produk, String satuan, String harga_jual, String harga_beli, String stok, String stok_minimum, String pic) {
        this.nama_produk = nama_produk;
        this.satuan = satuan;
        this.harga_jual = harga_jual;
        this.harga_beli = harga_beli;
        this.stok = stok;
        this.stok_minimum = stok_minimum;
        this.pic = pic;
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

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getStok_minimum() {
        return stok_minimum;
    }

    public void setStok_minimum(String stok_minimum) {
        this.stok_minimum = stok_minimum;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(String harga_jual) { this.harga_jual = harga_jual;}

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(String harga_beli) {
        this.harga_beli = harga_beli;
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
        return this.nama_produk;
    }
}
