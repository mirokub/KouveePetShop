package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.produk.ResultProduk;
import com.example.kouveepetshop.result.produk.ResultOneProduk;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiProduk {

    @POST("produk/{gambar}")
    Call<RequestBody> uploadImage(@Part MultipartBody.Part part, @Part("gambar") RequestBody requestBody);

    @GET("produk")
    Call<ResultProduk> getAllProduk();

    @GET("produk/{id}")
    Call<ResultProduk> getProduk(@Path("id") String id_produk);

    @POST("produk")
    Call<ResultOneProduk> createProduk(@Body ProdukModel produkModel);

    @PUT("produk/{id}")
    Call<ResultOneProduk> updateProduk(@Path("id") String id_produk,
                                         @Body ProdukModel produkModel);

    @PUT("produk/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneProduk> deleteProduk(@Path("id") String id_produk,
                                         @Field("pic") String pic);

}
