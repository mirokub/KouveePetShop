package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUkuranHewan {

    @GET("ukuranhewan")
    Call<ResultUkuranHewan> getAllUkuranHewan();

    @GET("ukuranhewan/{id}")
    Call<ResultOneUkuran> getUkuranHewan(@Path("id") String id_ukuran);

    @POST("ukuranhewan")
    Call<ResultOneUkuran> createUkuranHewan(@Body UkuranHewanModel ukuranHewanModel);

    @PUT("ukuranhewan/{id}")
    Call<ResultOneUkuran> updateUkuranHewan(@Path("id") String id_ukuran,
                                            @Body UkuranHewanModel ukuranHewanModel);

    @PUT("ukuranhewan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneUkuran> deleteUkuranHewan(@Path("id") String id_ukuran,
                                            @Field("pic") String pic);
}
