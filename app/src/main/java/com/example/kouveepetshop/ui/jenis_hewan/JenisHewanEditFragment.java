package com.example.kouveepetshop.ui.jenis_hewan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisHewanEditFragment extends Fragment {

    private String id_jenis, jenis, pic;
    View myView;
    EditText mJenis;
    Button mBtnSaveEditJenisHewan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_jenis_hewan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnSaveEditJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jenis = mJenis.getText().toString();

                if(validate(jenis)){
                    JenisHewanModel jenisHewanModel = new JenisHewanModel(jenis, pic);
                    updateJenis(id_jenis, jenisHewanModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mJenis = myView.findViewById(R.id.txtEditJenis);
        mBtnSaveEditJenisHewan = myView.findViewById(R.id.btnSaveEditJenis);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_jenis = nBundle.getString("id_jenis");
        mJenis.setText(nBundle.getString("jenis"));
    }

    private boolean validate(String jenis){
        if(jenis == null || jenis.trim().length() == 0){
            mJenis.setError("Jenis Hewan is required");
            return false;
        }
        return true;
    }

    private void updateJenis(String id_jenis, JenisHewanModel jenisHewanModel){
        ApiJenisHewan apiJenisHewan = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultOneJenis> jenisCall = apiJenisHewan.updateJenisHewan(id_jenis, jenisHewanModel);

        jenisCall.enqueue(new Callback<ResultOneJenis>() {
            @Override
            public void onResponse(Call<ResultOneJenis> call, Response<ResultOneJenis> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Jenis Hewan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new JenisHewanViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Jenis Hewan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneJenis> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
