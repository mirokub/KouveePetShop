package com.example.kouveepetshop.api;

import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUkuranHewan {

    @GET("ukuranhewan")
    Call<ResultUkuranHewan> getAllUkuranHewan();

    @PUT("ukuran/delete/{id}")
    @FormUrlEncoded
    static Call<ResultOneUkuran> deleteUkuranHewan(@Path("id") String id_Ukuran,
                                                   @Field("pic") String pic) {
        return null;
    }

    Call<ResultOneUkuran> createUkuran(String ukuranHewanModel);
}
