package com.example.kouveepetshop.ui.jenis_hewan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.result.jenis_hewan.ResultOneJenis;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisHewanAddFragment extends Fragment {
    private String pic;
    View myView;
    EditText mJenis;
    Button mBtnSaveJenis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_jenis_hewan_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();

        mBtnSaveJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jenis = mJenis.getText().toString();

                if(validate(jenis)){
                    JenisHewanModel jenisHewanModel = new JenisHewanModel(jenis, pic);
                    saveJenis(jenisHewanModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mJenis = myView.findViewById(R.id.txtAddJenis);
        mBtnSaveJenis = myView.findViewById(R.id.btnSaveAddJenis);
    }

    private boolean validate(String jenis){
        if(jenis == null || jenis.trim().length() == 0){
            mJenis.setError("Jenis Hewan is required");
            return false;
        }

        return true;
    }

    private void saveJenis(JenisHewanModel jenisHewanModel){
        ApiJenisHewan apiJenis = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultOneJenis> jenisCall = apiJenis.createJenisHewan(jenisHewanModel);

        jenisCall.enqueue(new Callback<ResultOneJenis>() {
            @Override
            public void onResponse(Call<ResultOneJenis> call, Response<ResultOneJenis> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Jenis Hewan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new JenisHewanViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Jenis Hewan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneJenis> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
