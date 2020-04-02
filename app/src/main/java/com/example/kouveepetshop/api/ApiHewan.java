package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.result.hewan.ResultHewan;
import com.example.kouveepetshop.result.hewan.ResultOneHewan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiHewan {

    @GET("hewan")
    Call<ResultHewan> getAllHewan();

    @GET("hewan/{id}")
    Call<ResultOneHewan> getHewan(@Path("id") String id_hewan);

    @POST("hewan")
    Call<ResultOneHewan> createHewan(@Body HewanModel hewanModel);

    @PUT("hewan/{id}")
    Call<ResultOneHewan> updateHewan(@Path("id") String id_hewan,
                                     @Body HewanModel hewanModel);

    @PUT("hewan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneHewan> deleteHewan(@Path("id") String id_hewan,
                                     @Field("pic") String pic);
}
