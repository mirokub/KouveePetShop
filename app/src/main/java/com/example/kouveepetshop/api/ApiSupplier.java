package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.supplier.ResultSupplier;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSupplier {

    @GET("supplier")
    Call<ResultSupplier> getAllSupplier();

    @GET("supplier/{id}")
    Call<ResultOneSupplier> getSupplier(@Path("id") String id_supplier);

    @POST("supplier")
    Call<ResultOneSupplier> createSupplier(@Body SupplierModel supplierModel);

    @PUT("supplier/{id}")
    Call<ResultOneSupplier> updateSupplier(@Path("id") String id_supplier,
                                     @Body SupplierModel supplierModel);

    @PUT("supplier/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneSupplier> deleteSupplier(@Path("id") String id_supplier,
                                     @Field("pic") String pic);
}
