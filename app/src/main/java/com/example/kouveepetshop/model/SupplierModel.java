package com.example.kouveepetshop.model;

import com.google.gson.annotations.SerializedName;

public class SupplierModel {

    @SerializedName("id_supplier")
    private String id_supplier;

    @SerializedName("nama_supplier")
    private String nama_supplier;

    @SerializedName("alamat")
    private String alamat;

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

    public SupplierModel() {
    }

    public SupplierModel(String nama_supplier, String alamat, String no_telp, String created_at, String updated_at, String edited_by) {
        this.nama_supplier = nama_supplier;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
    }

    public SupplierModel(String nama_supplier, String alamat, String no_telp, String pic) {
        this.nama_supplier = nama_supplier;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.pic = pic;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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
}
