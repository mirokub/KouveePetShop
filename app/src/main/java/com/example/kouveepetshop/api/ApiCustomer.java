package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCustomer {

    @GET("customer")
    Call<ResultCustomer> getAllCustomer();

    @GET("customer/{id}")
    Call<ResultOneCustomer> getCustomer(@Path("id") String id_customer);

    @POST("customer")
    Call<ResultOneCustomer> createCustomer(@Body CustomerModel customerModel);

    @PUT("customer/{id}")
    Call<ResultOneCustomer> updateCustomer(@Path("id") String id_customer,
                                           @Body CustomerModel customerModel);

    @PUT("customer/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneCustomer> deleteCustomer(@Path("id") String id_customer,
                                           @Field("pic") String pic);
}
