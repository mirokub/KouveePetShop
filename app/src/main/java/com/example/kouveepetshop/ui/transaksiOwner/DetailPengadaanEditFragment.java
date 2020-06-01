package com.example.kouveepetshop.ui.transaksiOwner;

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
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.pengadaan.ResultOneDetailPengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultOnePengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultOneDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.result.produk.ResultProduk;
import com.example.kouveepetshop.ui.transaksiCS.DetailProdukViewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengadaanEditFragment extends Fragment {

    private String nomor_pengadaan, nama_supplier, tgl_pengadaan, total, status_cetak, pic;
    private String id_detail, nama_produk, jumlah_beli, subtotal;
    PengadaanModel pengadaanModel = new PengadaanModel();
    View myView;
    EditText mJumlahBeli;
    Spinner mProduk;
    Button mBtnUpdateDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_pengadaan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPengeluaran, jumlahBeli, hargaProduk, subTotal;
                ProdukModel produk = getProdukSelected();
                String id_produk = produk.getId_produk();

                totalPengeluaran = Integer.parseInt(total);
                jumlahBeli = Integer.parseInt(mJumlahBeli.getText().toString());
                hargaProduk = Integer.parseInt(produk.getHarga_beli());

                totalPengeluaran = totalPengeluaran - Integer.parseInt(subtotal);

                subTotal = jumlahBeli * hargaProduk;
                totalPengeluaran = totalPengeluaran + subTotal;

                if(validate(String.valueOf(jumlahBeli))){
                    DetailPengadaanModel detail = new DetailPengadaanModel(nomor_pengadaan, String.valueOf(id_produk), String.valueOf(jumlahBeli), String.valueOf(subTotal));
                    save(id_detail, nomor_pengadaan, String.valueOf(totalPengeluaran), detail);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mProduk = myView.findViewById(R.id.spinnerPilihProdukPengadaanEdit);
        mJumlahBeli = myView.findViewById(R.id.etPembelianPengadaanEdit);
        mBtnUpdateDetail = myView.findViewById(R.id.btnUpdateDetailPengadaan);
        Bundle nBundle = getArguments();
        nomor_pengadaan = nBundle.getString("nomor_pengadaan");
        nama_supplier = nBundle.getString("nama_supplier");
        tgl_pengadaan = nBundle.getString("tgl_pengadaan");
        total = nBundle.getString("total");
        status_cetak = nBundle.getString("status_cetak");
        id_detail = nBundle.getString("id_detail");
        nama_produk = nBundle.getString("nama_produk");
        jumlah_beli = nBundle.getString("jumlah_beli");
        subtotal = nBundle.getString("subtotal");
    }

    private void setText(){
        setSpinnerProduk(nama_produk);
        mJumlahBeli.setText(jumlah_beli);
    }

    private void save(String id_detail, String idTransaksi, String totalPengeluaran, DetailPengadaanModel detail){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOnePengadaan> pengadaanCall = apiPengadaan.updateTotal(idTransaksi, totalPengeluaran);

        pengadaanCall.enqueue(new Callback<ResultOnePengadaan>() {
            @Override
            public void onResponse(Call<ResultOnePengadaan> call, Response<ResultOnePengadaan> response) {
                if(response.isSuccessful()){
                    pengadaanModel = response.body().getPengadaanModel();
                    updateDetail(id_detail, detail, totalPengeluaran);
                }else{

                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultOnePengadaan> call, Throwable t) {
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

    private void updateDetail(String id_detail, DetailPengadaanModel detail, String totalPengeluaran){
        ApiPengadaan apiDetail = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOneDetailPengadaan> detailCall = apiDetail.updateDetail(id_detail, detail);

        detailCall.enqueue(new Callback<ResultOneDetailPengadaan>() {
            @Override
            public void onResponse(Call<ResultOneDetailPengadaan> call, Response<ResultOneDetailPengadaan> response) {
                if(response.isSuccessful()){
                    Bundle mBundle = new Bundle();
                    mBundle.putString("nomor_pengadaan", nomor_pengadaan);
                    mBundle.putString("nama_supplier", nama_supplier);
                    mBundle.putString("tgl_pengadaan", tgl_pengadaan);
                    mBundle.putString("total", totalPengeluaran);
                    mBundle.putString("status_cetak", status_cetak);
                    DetailPengadaanViewFragment detailPengadaanViewFragment = new DetailPengadaanViewFragment();
                    detailPengadaanViewFragment.setArguments(mBundle);
                    Toast.makeText(getActivity(), "Update Item Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, detailPengadaanViewFragment).commit();
                }else{
                    System.out.println("Code: " + response.code());
                    Toast.makeText(getActivity(), "Update Item Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneDetailPengadaan> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
