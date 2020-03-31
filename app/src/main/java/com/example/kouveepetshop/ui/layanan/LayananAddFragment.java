package com.example.kouveepetshop.ui.layanan;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananAddFragment extends Fragment {

    private String pic;
    View myView;
    EditText mNamaLayanan, mHarga;
    Spinner mJenisHewan, mUkuranHewan;
    Button mBtnSaveLayanan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_layanan_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setSpinnerJenis();
        setSpinnerUkuran();

        mBtnSaveLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaLayanan = mNamaLayanan.getText().toString();
                String id_jenis = getJenisSelected();
                String id_ukuran = getUkuranSelected();
                String harga = mHarga.getText().toString();

                if(validate(namaLayanan, harga)){
                    LayananModel layananModel = new LayananModel(namaLayanan, id_jenis, id_ukuran, harga, pic);
                    saveLayanan(layananModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mNamaLayanan = myView.findViewById(R.id.etNamaLayanan);
        mJenisHewan = myView.findViewById(R.id.spinnerJenisHewan);
        mUkuranHewan = myView.findViewById(R.id.spinnerUkuranHewan);
        mHarga = myView.findViewById(R.id.etHarga);
        mBtnSaveLayanan = myView.findViewById(R.id.btnSaveLayanan);
    }

    private void setSpinnerJenis(){
        ApiJenisHewan apiJenisHewan = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultJenisHewan> jenisHewanCall = apiJenisHewan.getAllJenisHewan();

        jenisHewanCall.enqueue(new Callback<ResultJenisHewan>() {
            @Override
            public void onResponse(Call<ResultJenisHewan> call, Response<ResultJenisHewan> response) {
                if(response.isSuccessful()){
                    List<JenisHewanModel> jenisHewan = response.body().getJenisHewanModels();
                    ArrayAdapter<JenisHewanModel> adapter = new ArrayAdapter<JenisHewanModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, jenisHewan);
                    mJenisHewan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultJenisHewan> call, Throwable t) {

            }
        });
    }

    public void setSpinnerUkuran(){
        ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultUkuranHewan> ukuranHewanCall = apiUkuranHewan.getAllUkuranHewan();

        ukuranHewanCall.enqueue(new Callback<ResultUkuranHewan>() {
            @Override
            public void onResponse(Call<ResultUkuranHewan> call, Response<ResultUkuranHewan> response) {
                if(response.isSuccessful()){
                    List<UkuranHewanModel> ukuranHewan = response.body().getUkuranHewanModels();
                    ArrayAdapter<UkuranHewanModel> adapter = new ArrayAdapter<UkuranHewanModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ukuranHewan);
                    mUkuranHewan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultUkuranHewan> call, Throwable t) {

            }
        });
    }

    private String getJenisSelected(){
        JenisHewanModel jenisHewanModel = (JenisHewanModel) mJenisHewan.getSelectedItem();
        return jenisHewanModel.getId_jenis();
    }

    private String getUkuranSelected(){
        UkuranHewanModel ukuranHewanModel = (UkuranHewanModel) mUkuranHewan.getSelectedItem();
        return ukuranHewanModel.getId_ukuran();
    }

    private boolean validate(String namaLayanan, String harga){
        if(namaLayanan == null || namaLayanan.trim().length() == 0){
            mNamaLayanan.setError("Nama Layanan is required");
            return false;
        }
        if(harga == null || harga.trim().length() == 0){
            mHarga.setError("Harga is required");
            return false;
        }
        return true;
    }

    private void saveLayanan(LayananModel layananModel){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultOneLayanan> layananCall = apiLayanan.createLayanan(layananModel);

        layananCall.enqueue(new Callback<ResultOneLayanan>() {
            @Override
            public void onResponse(Call<ResultOneLayanan> call, Response<ResultOneLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Layanan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new LayananViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Layanan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneLayanan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
