package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.penjualan_layanan.ResultDetailLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOneDetailLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultPenjualanLayanan;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;
import com.example.kouveepetshop.result.supplier.ResultSupplier;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPenjualanLayanan {

    //Penjualan Layanan

    @GET("transaksi/layanan")
    Call<ResultPenjualanLayanan> getAll();

    @GET("transaksi/layanan/{id}")
    Call<ResultOnePenjualanLayanan> getPenjualanLayanan(@Path("id") String id);

    @POST("transaksi/layanan")
    Call<ResultOnePenjualanLayanan> createPenjualanLayanan(@Body PenjualanLayananModel penjualanLayananModel);

    @PUT("transaksi/layanan/{id}")
    Call<ResultOnePenjualanLayanan> updatePenjualanLayanan(@Path("id") String id,
                                                           @Body PenjualanLayananModel penjualanLayananModel);

    @PUT("transaksi/layanan/changeStatus/{id}")
    @FormUrlEncoded
    Call<ResultOnePenjualanLayanan> changeStatus(@Path("id") String id,
                                                 @Field("status_layanan") String status_layanan,
                                                 @Field("id_cs") String id_cs);

    @PUT("transaksi/layanan/updateTotal/{id}")
    @FormUrlEncoded
    Call<ResultOnePenjualanLayanan> updateTotal(@Path("id") String nomor_transaksi,
                                                @Field("total") String total);

    @PUT("transaksi/layanan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOnePenjualanLayanan> deletePenjualanLayanan(@Path("id") String id,
                                                           @Field("id_cs") String id_cs);

    //Detail Penjualan Layanan

    @GET("transaksi/detail_layanan/getByTransaction/{id}")
    Call<ResultDetailLayanan> getAllDetail(@Path("id") String nomor_transaksi);

    @GET("transaksi/detail_layanan/{id}")
    Call<ResultOneDetailLayanan> getDetail(@Path("id") String id_detail);

    @POST("transaksi/detail_layanan")
    Call<ResultOneDetailLayanan> createDetailLayanan(@Body DetailPenjualanLayananModel detailPenjualanLayananModel);

    @PUT("transaksi/detail_layanan/{id}")
    Call<ResultOneDetailLayanan> updateDetailLayanan(@Path("id") String id_detail,
                                                     @Body DetailPenjualanLayananModel detailPenjualanLayananModel);

    @DELETE("transaksi/detail_layanan/{id}")
    Call<ResultOneDetailLayanan> deleteDetailLayanan(@Path("id") String id_detail);

}
