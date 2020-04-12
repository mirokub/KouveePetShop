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
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananEditFragment extends Fragment {

    private String id_layanan, pic;
    View myView;
    EditText mNamaLayanan, mHarga;
    Spinner mJenisHewan, mUkuranHewan;
    Button mBtnUpdateLayanan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_layanan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaLayanan = mNamaLayanan.getText().toString();
                String id_jenis = getJenisSelected();
                String id_ukuran = getUkuranSelected();
                String harga = mHarga.getText().toString();

                if(validate(namaLayanan, harga)){
                    LayananModel layananModel = new LayananModel(namaLayanan, id_jenis, id_ukuran, harga, pic);
                    updateLayanan(id_layanan, layananModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mNamaLayanan = myView.findViewById(R.id.etNamaLayananEdit);
        mJenisHewan = myView.findViewById(R.id.spinnerJenisHewanEdit);
        mUkuranHewan = myView.findViewById(R.id.spinnerUkuranHewanEdit);
        mHarga = myView.findViewById(R.id.etHargaEdit);
        mBtnUpdateLayanan = myView.findViewById(R.id.btnUpdateLayanan);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_layanan = nBundle.getString("id_layanan");
        mNamaLayanan.setText(nBundle.getString("nama_layanan"));
        setSpinnerJenis(nBundle.getString("jenis_hewan"));
        setSpinnerUkuran(nBundle.getString("ukuran_hewan"));
        mHarga.setText(nBundle.getString("harga"));
    }

    private void setSpinnerJenis(final String jenisSelected){
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
                    List<UkuranHewanModel> ukuranHewan = response.body().getListUkuranHewan();
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

    private void updateLayanan(String id_layanan, LayananModel layananModel){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultOneLayanan> layananCall = apiLayanan.updateLayanan(id_layanan, layananModel);

        layananCall.enqueue(new Callback<ResultOneLayanan>() {
            @Override
            public void onResponse(Call<ResultOneLayanan> call, Response<ResultOneLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Layanan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new LayananViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Layanan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneLayanan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
