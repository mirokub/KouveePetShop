package com.example.kouveepetshop.api;

import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiUkuranHewan {

    @GET("ukuranhewan")
    Call<ResultUkuranHewan> getAllUkuranHewan();
}
