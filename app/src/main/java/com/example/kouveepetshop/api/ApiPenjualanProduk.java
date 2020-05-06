package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.result.penjualan_produk.ResultDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOneDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultPenjualanProduk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPenjualanProduk {

    //Penjualan Produk

    @GET("transaksi/produk")
    Call<ResultPenjualanProduk> getAll();

    @GET("transaksi/produk/{id}")
    Call<ResultOnePenjualanProduk> getPenjualanProduk(@Path("id") String id);

    @POST("transaksi/produk")
    Call<ResultOnePenjualanProduk> createPenjualanProduk(@Body PenjualanProdukModel penjualanProdukModel);

    @PUT("transaksi/produk/{id}")
    Call<ResultOnePenjualanProduk> updatePenjualanProduk(@Path("id") String id,
                                                           @Body PenjualanProdukModel penjualanProdukModel);

    @PUT("transaksi/produk/updateTotal/{id}")
    @FormUrlEncoded
    Call<ResultOnePenjualanProduk> updateTotal(@Path("id") String nomor_transaksi,
                                                @Field("total") String total);

    @PUT("transaksi/produk/delete/{id}")
    @FormUrlEncoded
    Call<ResultOnePenjualanProduk> deletePenjualanProduk(@Path("id") String id,
                                                           @Field("id_cs") String id_cs);

    //Detail Penjualan Produk

    @GET("transaksi/detail_produk")
    Call<ResultDetailProduk> getAllDetail(String nomor_transaksi);

    @GET("transaksi/detail_produk/{id}")
    Call<ResultOneDetailProduk> getDetail(@Path("id") String id_detail);

    @GET("transaksi/detail_produk/getByTransaction/{id}")
    Call<ResultDetailProduk> getAllDetailByTransaction(@Path("id") String nomor_transaksi);

    @POST("transaksi/detail_produk")
    Call<ResultOneDetailProduk> createDetailProduk(@Body DetailPenjualanProdukModel detailPenjualanProdukModel);

    @PUT("transaksi/detail_produk/{id}")
    Call<ResultOneDetailProduk> updateDetailProduk(@Path("id") String id_detail,
                                                     @Body DetailPenjualanProdukModel detailPenjualanProdukModel);

    @DELETE("transaksi/detail_produk/{id}")
    Call<ResultOneDetailProduk> deleteDetailProduk(@Path("id") String id_detail);

}
