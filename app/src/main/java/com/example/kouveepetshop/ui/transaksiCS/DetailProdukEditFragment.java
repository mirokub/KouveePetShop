package com.example.kouveepetshop.ui.transaksiCS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.produk.ResultProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOneDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukEditFragment extends Fragment {

    private String id, nomor_transaksi, tgl_penjualan, status_pembayaran, id_cs;
    private String id_detail, nama_produk, jumlah_beli, subtotal;
    PenjualanProdukModel penjualanProduk = new PenjualanProdukModel();
    View myView;
    EditText mJumlahBeli;
    Spinner mProduk;
    Button mBtnUpdateDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_produk_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        id_cs = SP.getSpId();

        setAtribut();
        setText();
        getPenjualanProduk();

        mBtnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalBiaya, jumlahBeli, hargaProduk, subTotal;
                ProdukModel produk = getProdukSelected();
                String id_produk = produk.getId_produk();

                totalBiaya = Integer.parseInt(penjualanProduk.getTotal());
                jumlahBeli = Integer.parseInt(mJumlahBeli.getText().toString());
                hargaProduk = Integer.parseInt(produk.getHarga_jual());

                totalBiaya = totalBiaya - Integer.parseInt(subtotal);

                subTotal = jumlahBeli * hargaProduk;
                totalBiaya = totalBiaya + subTotal;

                if(validate(String.valueOf(jumlahBeli))){
                    DetailPenjualanProdukModel detail = new DetailPenjualanProdukModel(nomor_transaksi, String.valueOf(id_produk), String.valueOf(jumlahBeli), String.valueOf(subTotal));
                    save(id_detail, nomor_transaksi, String.valueOf(totalBiaya), detail);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mProduk = myView.findViewById(R.id.spinnerPilihProdukEdit);
        mJumlahBeli = myView.findViewById(R.id.etPembelianProdukEdit);
        mBtnUpdateDetail = myView.findViewById(R.id.btnUpdateDetailProduk);
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        id_detail = nBundle.getString("id_detail");
        nomor_transaksi = nBundle.getString("nomor_transaksi");
        tgl_penjualan = nBundle.getString("tgl_penjualan");
        status_pembayaran = nBundle.getString("status_pembayaran");
        nama_produk = nBundle.getString("nama_produk");
        jumlah_beli = nBundle.getString("jumlah_beli");
        subtotal = nBundle.getString("subtotal");
    }

    private void setText(){
        setSpinnerProduk(nama_produk);
        mJumlahBeli.setText(jumlah_beli);
    }

    private void getPenjualanProduk(){
        ApiPenjualanProduk apiPenjualan = ApiClient.getClient().create(ApiPenjualanProduk.class);
        Call<ResultOnePenjualanProduk> penjualanCall = apiPenjualan.getPenjualanProduk(id);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanProduk>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanProduk> call, Response<ResultOnePenjualanProduk> response) {
                if(response.isSuccessful()){
                    penjualanProduk = response.body().getPenjualanProdukModel();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanProduk> call, Throwable t) {

            }
        });
    }

    private void save(String id_detail, String idTransaksi, String total, DetailPenjualanProdukModel detail){
        ApiPenjualanProduk apiPenjualan = ApiClient.getClient().create(ApiPenjualanProduk.class);
        Call<ResultOnePenjualanProduk> penjualanCall = apiPenjualan.updateTotal(idTransaksi, total);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanProduk>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanProduk> call, Response<ResultOnePenjualanProduk> response) {
                if(response.isSuccessful()){
                    penjualanProduk = response.body().getPenjualanProdukModel();
                    updateDetail(id_detail, detail);
                }else{
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanProduk> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
            }
        });
    }

    private void setSpinnerProduk(String produkSelected){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultProduk> produkCall = apiProduk.getAllProduk();

        produkCall.enqueue(new Callback<ResultProduk>() {
            @Override
            public void onResponse(Call<ResultProduk> call, Response<ResultProduk> response) {
                if(response.isSuccessful()){
                    List<ProdukModel> produk = response.body().getListProduk();
                    ArrayAdapter<ProdukModel> adapter = new ArrayAdapter<ProdukModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, produk);
                    mProduk.setAdapter(adapter);
                    setProdukSelected(adapter, produkSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultProduk> call, Throwable t) {

            }
        });
    }

    private void setProdukSelected(ArrayAdapter<ProdukModel> adapter, String produkSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(produkSelected)){
                mProduk.setSelection(i);
                break;
            }
        }
    }

    private ProdukModel getProdukSelected(){
        ProdukModel produk = (ProdukModel) mProduk.getSelectedItem();
        return produk;
    }

    private boolean validate(String jumlahBeli){
        if(jumlahBeli == null || jumlahBeli.trim().length() == 0){
            mJumlahBeli.setError("Jumlah pembelian is required");
            return false;
        }
        return true;
    }

    private void updateDetail(String id_detail, DetailPenjualanProdukModel detail){
        ApiPenjualanProduk apiDetail = ApiClient.getClient().create(ApiPenjualanProduk.class);
        Call<ResultOneDetailProduk> detailCall = apiDetail.updateDetailProduk(id_detail, detail);

        detailCall.enqueue(new Callback<ResultOneDetailProduk>() {
            @Override
            public void onResponse(Call<ResultOneDetailProduk> call, Response<ResultOneDetailProduk> response) {
                if(response.isSuccessful()){
                    Bundle mBundle = new Bundle();
                    mBundle.putString("id", id);
                    mBundle.putString("nomor_transaksi", nomor_transaksi);
                    mBundle.putString("tgl_penjualan", tgl_penjualan);
                    mBundle.putString("status_pembayaran", status_pembayaran);
                    DetailProdukViewFragment detailProdukViewFragment = new DetailProdukViewFragment();
                    detailProdukViewFragment.setArguments(mBundle);
                    Toast.makeText(getActivity(), "Update Item Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailProdukViewFragment).commit();
                }else{
                    System.out.println("Code: " + response.code());
                    Toast.makeText(getActivity(), "Update Item Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneDetailProduk> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
