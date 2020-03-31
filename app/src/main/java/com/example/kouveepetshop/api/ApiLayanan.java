package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.result.layanan.ResultLayanan;
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiLayanan {

    @GET("layanan")
    Call<ResultLayanan> getAllLayanan();

    @GET("layanan/{id}")
    Call<ResultLayanan> getLayanan(@Path("id") String id_layanan);

    @POST("layanan")
    Call<ResultOneLayanan> createLayanan(@Body LayananModel layananModel);

    @PUT("layanan/{id}")
    Call<ResultOneLayanan> updateLayanan(@Path("id") String id_layanan,
                                         @Body LayananModel layananModel);

    @PUT("layanan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneLayanan> deleteLayanan(@Path("id") String id_layanan,
                                         @Field("pic") String pic);
}
