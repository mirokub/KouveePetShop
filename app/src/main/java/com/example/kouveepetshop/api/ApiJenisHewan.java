package com.example.kouveepetshop.api;

import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiJenisHewan {

    @GET("jenishewan")
    Call<ResultJenisHewan> getAllJenisHewan();
}
