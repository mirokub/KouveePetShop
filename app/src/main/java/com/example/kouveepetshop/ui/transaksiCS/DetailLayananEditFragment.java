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
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.api.ApiPenjualanLayanan;
import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.layanan.ResultLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOneDetailLayanan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLayananEditFragment extends Fragment {

    private String id, nomor_transaksi, tgl_penjualan, status_pembayaran, id_cs;
    private String id_detail, nama_layanan, jumlah_beli, subtotal;
    PenjualanLayananModel penjualanLayanan = new PenjualanLayananModel();
    View myView;
    EditText mJumlahBeli;
    Spinner mLayanan;
    Button mBtnUpdateDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_layanan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        id_cs = SP.getSpId();

        setAtribut();
        setText();
        getPenjualanLayanan();

        mBtnUpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalBiaya, jumlahBeli, hargaLayanan, subTotal;
                LayananModel layanan = getLayananSelected();
                String id_layanan = layanan.getId_layanan();

                totalBiaya = Integer.parseInt(penjualanLayanan.getTotal());
                jumlahBeli = Integer.parseInt(mJumlahBeli.getText().toString());
                hargaLayanan = Integer.parseInt(layanan.getHarga());

                totalBiaya = totalBiaya - Integer.parseInt(subtotal);

                subTotal = jumlahBeli * hargaLayanan;
                totalBiaya = totalBiaya + subTotal;

                if(validate(String.valueOf(jumlahBeli))){
                    DetailPenjualanLayananModel detail = new DetailPenjualanLayananModel(nomor_transaksi, String.valueOf(id_layanan), String.valueOf(jumlahBeli), String.valueOf(subTotal));
                    save(id_detail, nomor_transaksi, String.valueOf(totalBiaya), detail);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mLayanan = myView.findViewById(R.id.spinnerPilihLayananEdit);
        mJumlahBeli = myView.findViewById(R.id.etPembelianLayananEdit);
        mBtnUpdateDetail = myView.findViewById(R.id.btnUpdateDetailLayanan);
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        id_detail = nBundle.getString("id_detail");
        nomor_transaksi = nBundle.getString("nomor_transaksi");
        tgl_penjualan = nBundle.getString("tgl_penjualan");
        status_pembayaran = nBundle.getString("status_pembayaran");
        nama_layanan = nBundle.getString("nama_layanan");
        jumlah_beli = nBundle.getString("jumlah_beli");
        subtotal = nBundle.getString("subtotal");
    }

    private void setText(){
        setSpinnerLayanan(nama_layanan);
        mJumlahBeli.setText(jumlah_beli);
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

    private void save(String id_detail, String idTransaksi, String total, DetailPenjualanLayananModel detail){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.updateTotal(idTransaksi, total);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    penjualanLayanan = response.body().getPenjualanLayananModel();
                    updateDetail(id_detail, detail);
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

    private void setSpinnerLayanan(String layananSelected){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultLayanan> layananCall = apiLayanan.getAllLayanan();

        layananCall.enqueue(new Callback<ResultLayanan>() {
            @Override
            public void onResponse(Call<ResultLayanan> call, Response<ResultLayanan> response) {
                if(response.isSuccessful()){
                    List<LayananModel> layanan = response.body().getListLayanan();
                    ArrayAdapter<LayananModel> adapter = new ArrayAdapter<LayananModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, layanan);
                    mLayanan.setAdapter(adapter);
                    setLayananSelected(adapter, layananSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultLayanan> call, Throwable t) {

            }
        });
    }

    private void setLayananSelected(ArrayAdapter<LayananModel> adapter, String layananSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(layananSelected)){
                mLayanan.setSelection(i);
                break;
            }
        }
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

    private void updateDetail(String id_detail, DetailPenjualanLayananModel detail){
        ApiPenjualanLayanan apiDetail = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOneDetailLayanan> detailCall = apiDetail.updateDetailLayanan(id_detail, detail);

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
                    Toast.makeText(getActivity(), "Update Item Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailLayananViewFragment).commit();
                }else{
                    System.out.println("Code: " + response.code());
                    Toast.makeText(getActivity(), "Update Item Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneDetailLayanan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
