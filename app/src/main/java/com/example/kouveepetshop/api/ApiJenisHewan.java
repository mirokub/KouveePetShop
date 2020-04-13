package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.result.hewan.ResultOneHewan;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;
import com.example.kouveepetshop.result.jenis_hewan.ResultOneJenis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiJenisHewan {

    @GET("jenishewan")
    Call<ResultJenisHewan> getAllJenisHewan();

    @GET("jenishewan/{id}")
    Call<ResultOneJenis> getJenisHewan(@Path("id") String id_jenis);

    @POST("jenishewan")
    Call<ResultOneJenis> createJenisHewan(@Body JenisHewanModel jenisHewanModel);

    @PUT("jenishewan/{id}")
    Call<ResultOneJenis> updateJenisHewan(@Path("id") String id_jenis,
                                          @Body JenisHewanModel jenisHewanModel);

    @PUT("jenishewan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneJenis> deleteJenisHewan(@Path("id") String id_jenis,
                                     @Field("pic") String pic);
}
