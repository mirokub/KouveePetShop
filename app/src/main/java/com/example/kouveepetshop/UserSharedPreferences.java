package com.example.kouveepetshop;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {
    public static final String SP_KOUVEE_PETSHOP = "spKouveePetshop";
    public static final String SP_ID = "spId";
    public static final String SP_NAMA_PEGAWAI = "spNamaPegawai";
    public static final String SP_USERNAME = "spUsername";
    public static final String SP_JABATAN = "spJabatan";
    public static final String SP_ISLOGIN = "spIsLogin";

    SharedPreferences sp;
    public SharedPreferences.Editor spEditor;

    public UserSharedPreferences(Context context){
        sp = context.getSharedPreferences(SP_KOUVEE_PETSHOP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpId(){
        return sp.getString(SP_ID, "");
    }

    public String getSpNamaPegawai(){
        return sp.getString(SP_NAMA_PEGAWAI, "");
    }

    public String getSpUsername(){
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpJabatan(){
        return sp.getString(SP_JABATAN, "");
    }

    public Boolean getSpIsLogin(){
        return sp.getBoolean(SP_ISLOGIN, false);
    }

}
