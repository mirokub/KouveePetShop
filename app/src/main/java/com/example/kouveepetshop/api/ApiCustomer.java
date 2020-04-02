package com.example.kouveepetshop.api;

import com.example.kouveepetshop.result.customer.ResultCustomer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCustomer {

    @GET("customer")
    Call<ResultCustomer> getAllCustomer();
}
