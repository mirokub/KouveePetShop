package com.example.kouveepetshop.api;

import com.example.kouveepetshop.result.pegawai.ResultPegawai;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiPegawai {

    @POST("login")
    @FormUrlEncoded
    Call<ResultPegawai> login(@Field("username") String username,
                              @Field("password") String password);

}
