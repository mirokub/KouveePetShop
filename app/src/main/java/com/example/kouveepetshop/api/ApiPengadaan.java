package com.example.kouveepetshop.api;

import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.result.pengadaan.ResultDetailPengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultOneDetailPengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultOnePengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultPengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOneDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultPenjualanProduk;
import com.example.kouveepetshop.result.produk.ResultProduk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPengadaan {

    //Penjualan Produk

    @GET("pengadaan")
    Call<ResultPengadaan> getAll();

    @GET("notification")
    Call<ResultProduk> getNotification();

    @GET("pengadaan/{id}")
    Call<ResultOnePengadaan> getPengadaan(@Path("id") String id);

    @POST("pengadaan")
    Call<ResultOnePengadaan> createPengadaan(@Body PengadaanModel pengadaanModel);

    @PUT("pengadaan/{id}")
    Call<ResultOnePengadaan> updatePengadaan(@Path("id") String id,
                                             @Body PengadaanModel pengadaanModel);

    @PUT("pengadaan/updateTotal/{id}")
    @FormUrlEncoded
    Call<ResultOnePengadaan> updateTotal(@Path("id") String nomor_pengadaan,
                                         @Field("total") String total);

    @PUT("pengadaan/delete/{id}")
    @FormUrlEncoded
    Call<ResultOnePengadaan> deletePengadaan(@Path("id") String id,
                                             @Field("pic") String pic);

    @PUT("pengadaan/konfirmasi/{id}")
    Call<ResultOnePengadaan> confirmationPengadaan(@Path("id") String id);



    //Detail Penjualan Produk
    @GET("detail_pengadaan/getByTransaction/{id}")
    Call<ResultDetailPengadaan> getAllDetail(@Path("id") String nomor_pengadaan);

    @GET("detail_pengadaan/{id}")
    Call<ResultOneDetailPengadaan> getDetail(@Path("id") String id_detail);

    @POST("detail_pengadaan")
    Call<ResultOneDetailPengadaan> createDetail(@Body DetailPengadaanModel detailPengadaanModel);

    @PUT("detail_pengadaan/{id}")
    Call<ResultOneDetailPengadaan> updateDetail(@Path("id") String id_detail,
                                                @Body DetailPengadaanModel detailPengadaanModel);

    @DELETE("detail_pengadaan/{id}")
    Call<ResultOneDetailPengadaan> deleteDetail(@Path("id") String id_detail);

    //Surat Pemesanan
    @GET("pengadaan/surat/print/{id}")
    Call<ResponseBody> printSuratPemesanan(@Path("id") String id);

}
