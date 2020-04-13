package com.example.kouveepetshop.ui.produk;

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
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.produk.ResultOneProduk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukEditFragment extends Fragment {


    private String id_produk, pic;
    View myView;
    EditText mNamaProduk, mHargaSatuan, mHargaJual, mHargaBeli;
    Button mBtnUpdateProduk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_produk_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaProduk = mNamaProduk.getText().toString();
                String hargaSatuan = mHargaSatuan.getText().toString();
                String hargaJual = mHargaJual.getText().toString();
                String hargaBeli = mHargaBeli.getText().toString();

                if(validate(namaProduk, hargaSatuan, hargaJual, hargaBeli)){
                    ProdukModel produkModel = new ProdukModel(namaProduk, hargaSatuan, hargaJual, hargaBeli, pic);
                    updateProduk(id_produk, produkModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mNamaProduk = myView.findViewById(R.id.etNamaProdukEdit);
        mHargaSatuan = myView.findViewById(R.id.etHargaSatuanEdit);
        mHargaJual = myView.findViewById(R.id.etHargaJualEdit);
        mHargaBeli = myView.findViewById(R.id.etHargaBeliEdit);
        mBtnUpdateProduk = myView.findViewById(R.id.btnUpdateProduk);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_produk = nBundle.getString("id_produk");
        mNamaProduk.setText(nBundle.getString("nama_produk"));
        mHargaSatuan.setText(nBundle.getString("satuan"));
        mHargaJual.setText(nBundle.getString("harga_jual"));
        mHargaBeli.setText(nBundle.getString("harga_beli"));
    }

    /*private void setSpinnerJenis(final String jenisSelected){
        ApiJenisHewan apiJenisHewan = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultJenisHewan> jenisHewanCall = apiJenisHewan.getAllJenisHewan();

        jenisHewanCall.enqueue(new Callback<ResultJenisHewan>() {
            @Override
            public void onResponse(Call<ResultJenisHewan> call, Response<ResultJenisHewan> response) {
                if(response.isSuccessful()){
                    List<JenisHewanModel> jenisHewan = response.body().getJenisHewanModels();
                    ArrayAdapter<JenisHewanModel> adapter = new ArrayAdapter<JenisHewanModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, jenisHewan);
                    mJenisHewan.setAdapter(adapter);
                    setJenisSelected(adapter, jenisSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultJenisHewan> call, Throwable t) {

            }
        });
    }

    public void setSpinnerUkuran(final String ukuranSelected){
        ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultUkuranHewan> ukuranHewanCall = apiUkuranHewan.getAllUkuranHewan();

        ukuranHewanCall.enqueue(new Callback<ResultUkuranHewan>() {
            @Override
            public void onResponse(Call<ResultUkuranHewan> call, Response<ResultUkuranHewan> response) {
                if(response.isSuccessful()){
                    List<UkuranHewanModel> ukuranHewan = response.body().getUkuranHewanModels();
                    ArrayAdapter<UkuranHewanModel> adapter = new ArrayAdapter<UkuranHewanModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ukuranHewan);
                    mUkuranHewan.setAdapter(adapter);
                    setUkuranSelected(adapter, ukuranSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultUkuranHewan> call, Throwable t) {

            }
        });
    }

    private void setJenisSelected(ArrayAdapter<JenisHewanModel> adapter, String jenisSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(jenisSelected)){
                mJenisHewan.setSelection(i);
                break;
            }
        }
    }

    private void setUkuranSelected(ArrayAdapter<UkuranHewanModel> adapter, String ukuranSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(ukuranSelected)){
                mUkuranHewan.setSelection(i);
                break;
            }
        }
    }

    private String getJenisSelected(){
        JenisHewanModel jenisHewanModel = (JenisHewanModel) mJenisHewan.getSelectedItem();
        return jenisHewanModel.getId_jenis();
    }

    private String getUkuranSelected(){
        UkuranHewanModel ukuranHewanModel = (UkuranHewanModel) mUkuranHewan.getSelectedItem();
        return ukuranHewanModel.getId_ukuran();
    }*/

    private boolean validate(String namaProduk, String hargaSatuan, String hargaJual, String hargaBeli){
        if(namaProduk == null || namaProduk.trim().length() == 0){
            mNamaProduk.setError("Nama Produk is required");
            return false;
        }
        if(hargaSatuan == null || hargaSatuan.trim().length() == 0){
            mHargaSatuan.setError("Harga Satuan is required");
            return false;
        }
        if(hargaJual == null || hargaJual.trim().length() == 0){
            mHargaJual.setError("Harga Jual is required");
            return false;
        }
        if(hargaBeli == null || hargaBeli.trim().length() == 0){
            mHargaBeli.setError("Harga Beli is required");
            return false;
        }
        return true;
    }

    private void updateProduk(String id_produk, ProdukModel produkModel){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultOneProduk> produkCall = apiProduk.updateProduk(id_produk, produkModel);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Produk Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Produk Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneProduk> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
