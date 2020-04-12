package com.example.kouveepetshop.ui.ukuran_hewan;

import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class UkuranHeawanAddFragment extends Fragment {

    private String pic;
    View myView;
    EditText mUkuranHewan;
    Button mBtnSaveUkuran;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_ukuran_hewan_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();


        mBtnSaveUkuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UkuranHewan = mUkuranHewan.getText().toString();

                if(validate(UkuranHewan)){
                    UkuranHewanModel ukuranHewanModel = new UkuranHewanModel(UkuranHewan, pic);
                    mBtnSaveUkuran(ukuranHewanModel);
                }
            }
        });

        return myView;
    }

    private void mBtnSaveUkuran(UkuranHewanModel ukuranHewanModel) {

    }


    private void setAtribut(){
        mUkuranHewan = myView.findViewById(R.id.etUkuranHewanEdit);
        mBtnSaveUkuran = myView.findViewById(R.id.btnSaveUkuran);
    }

    private boolean validate(String UkuranHewan){
        if(UkuranHewan == null || UkuranHewan.trim().length() == 0){
            mUkuranHewan.setError("Ukuran is required");
            return false;
        }
        return true;
    }

    private void saveUkuran(String ukuranHewanModel){
        ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultOneUkuran> ukuranCall = apiUkuranHewan.createUkuran(ukuranHewanModel);

        ukuranCall.enqueue(new Callback<ResultOneUkuran>() {
            @Override
            public void onResponse(Call<ResultOneUkuran> call, Response<ResultOneUkuran> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Ukuran Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new UkuranHewanViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Ukuran Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneUkuran> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
