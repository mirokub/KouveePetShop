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
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.api.ApiPenjualanLayanan;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;
import com.example.kouveepetshop.result.layanan.ResultLayanan;
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOneDetailLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLayananAddFragment extends Fragment {

    private String id, nomor_transaksi, tgl_penjualan, status_pembayaran, id_cs;
    PenjualanLayananModel penjualanLayanan = new PenjualanLayananModel();
    View myView;
    EditText mJumlahBeli;
    Spinner mLayanan;
    Button mBtnSaveDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_layanan_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        id_cs = SP.getSpId();

        setAtribut();
        setSpinnerLayanan();
        getPenjualanLayanan();

        mBtnSaveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalBiaya, jumlahBeli, hargaLayanan, subtotal;
                LayananModel layanan = getLayananSelected();
                String id_layanan = layanan.getId_layanan();

                totalBiaya = Integer.parseInt(penjualanLayanan.getTotal());
                jumlahBeli = Integer.parseInt(mJumlahBeli.getText().toString());
                hargaLayanan = Integer.parseInt(layanan.getHarga());

                subtotal = jumlahBeli * hargaLayanan;
                totalBiaya = totalBiaya + subtotal;


                if(validate(String.valueOf(jumlahBeli))){
                    DetailPenjualanLayananModel detail = new DetailPenjualanLayananModel(nomor_transaksi, String.valueOf(id_layanan), String.valueOf(jumlahBeli), String.valueOf(subtotal));
                    save(nomor_transaksi, String.valueOf(totalBiaya), detail);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mLayanan = myView.findViewById(R.id.spinnerPilihLayanan);
        mJumlahBeli = myView.findViewById(R.id.etPembelianLayanan);
        mBtnSaveDetail = myView.findViewById(R.id.btnSaveDetailLayanan);
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        nomor_transaksi = nBundle.getString("nomor_transaksi");
        tgl_penjualan = nBundle.getString("tgl_penjualan");
        status_pembayaran = nBundle.getString("status_pembayaran");
    }

    private void getPenjualanLayanan(){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.getPenjualanLayanan(id);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    penjualanLayanan = response.body().getPenjualanLayananModel();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanLayanan> call, Throwable t) {

            }
        });
    }

    private void save(String idTransaksi, String total, DetailPenjualanLayananModel detail){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.updateTotal(idTransaksi, total);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    penjualanLayanan = response.body().getPenjualanLayananModel();
                    saveDetail(detail);
                }else{
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanLayanan> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
            }
        });
    }

    private void setSpinnerLayanan(){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultLayanan> layananCall = apiLayanan.getAllLayanan();

        layananCall.enqueue(new Callback<ResultLayanan>() {
            @Override
            public void onResponse(Call<ResultLayanan> call, Response<ResultLayanan> response) {
                if(response.isSuccessful()){
                    List<LayananModel> layanan = response.body().getListLayanan();
                    ArrayAdapter<LayananModel> adapter = new ArrayAdapter<LayananModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, layanan);
                    mLayanan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultLayanan> call, Throwable t) {

            }
        });
    }

    private LayananModel getLayananSelected(){
        LayananModel layanan = (LayananModel) mLayanan.getSelectedItem();
        return layanan;
    }

    private boolean validate(String jumlahBeli){
        if(jumlahBeli == null || jumlahBeli.trim().length() == 0){
            mJumlahBeli.setError("Jumlah pembelian is required");
            return false;
        }
        return true;
    }

    private void saveDetail(DetailPenjualanLayananModel detail){
        ApiPenjualanLayanan apiDetail = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOneDetailLayanan> detailCall = apiDetail.createDetailLayanan(detail);

        detailCall.enqueue(new Callback<ResultOneDetailLayanan>() {
            @Override
            public void onResponse(Call<ResultOneDetailLayanan> call, Response<ResultOneDetailLayanan> response) {
                if(response.isSuccessful()){
                    Bundle mBundle = new Bundle();
                    mBundle.putString("id", id);
                    mBundle.putString("nomor_transaksi", nomor_transaksi);
                    mBundle.putString("tgl_penjualan", tgl_penjualan);
                    mBundle.putString("status_pembayaran", status_pembayaran);
                    DetailLayananViewFragment detailLayananViewFragment = new DetailLayananViewFragment();
                    detailLayananViewFragment.setArguments(mBundle);
                    Toast.makeText(getActivity(), "Adding Item Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailLayananViewFragment).commit();
                }else{
                    System.out.println("Code: " + response.code());
                    Toast.makeText(getActivity(), "Adding Item Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneDetailLayanan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
