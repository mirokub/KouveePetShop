package com.example.kouveepetshop.model;
import com.google.gson.annotations.SerializedName;

public class PegawaiModel {
    @SerializedName("id_pegawai")
    private String id_pegawai;

    @SerializedName("nama_pegawai")
    private String nama_pegawai;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("no_telp")
    private String no_telp;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("username")
    private String username;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("edited_by")
    private String edited_by;

    public PegawaiModel() {
    }

    public PegawaiModel(String id_pegawai, String nama_pegawai, String alamat, String tgl_lahir,
                        String no_telp, String jabatan, String username, String created_at,
                        String updated_at, String edited_by) {
        this.id_pegawai = id_pegawai;
        this.nama_pegawai = nama_pegawai;
        this.alamat = alamat;
        this.tgl_lahir = tgl_lahir;
        this.no_telp = no_telp;
        this.jabatan = jabatan;
        this.username = username;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.edited_by = edited_by;
    }

    public PegawaiModel(String nama_pegawai, String alamat, String tgl_lahir, String no_telp, String jabatan, String username, String edited_by) {
        this.nama_pegawai = nama_pegawai;
        this.alamat = alamat;
        this.tgl_lahir = tgl_lahir;
        this.no_telp = no_telp;
        this.jabatan = jabatan;
        this.username = username;
        this.edited_by = edited_by;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
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

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
