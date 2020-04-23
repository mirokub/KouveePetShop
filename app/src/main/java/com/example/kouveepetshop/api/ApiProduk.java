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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiProduk {

    @GET("produk")
    Call<ResultProduk> getAllProduk();

    @GET("produk/{id}")
    Call<ResultOneProduk> getProduk(@Path("id") String id_produk);

    @Multipart
    @POST("produk")
    Call<ResultOneProduk> createProduk(@Part MultipartBody.Part file,
                                       @Part("nama_produk") RequestBody nama_produk,
                                       @Part("satuan") RequestBody satuan,
                                       @Part("harga_jual") RequestBody harga_jual,
                                       @Part("harga_beli") RequestBody harga_beli,
                                       @Part("stok") RequestBody stok,
                                       @Part("stok_minimum") RequestBody stok_minimum,
                                       @Part("pic") RequestBody pic);


    @Multipart
    @POST("produk/{id}")
    Call<ResultOneProduk> updateProdukWithImage(@Path("id") String id_produk,
                                                @Part MultipartBody.Part file,
                                                @Part("nama_produk") RequestBody nama_produk,
                                                @Part("satuan") RequestBody satuan,
                                                @Part("harga_jual") RequestBody harga_jual,
                                                @Part("harga_beli") RequestBody harga_beli,
                                                @Part("stok") RequestBody stok,
                                                @Part("stok_minimum") RequestBody stok_minimum,
                                                @Part("pic") RequestBody pic);

    @POST("produk/{id}")
    Call<ResultOneProduk> updateProdukWithoutImage(@Path("id") String id_produk, @Body ProdukModel produkModel);

    @POST("produk/delete/{id}")
    @FormUrlEncoded
    Call<ResultOneProduk> deleteProduk(@Path("id") String id_produk,
                                       @Field("pic") String pic);

}
