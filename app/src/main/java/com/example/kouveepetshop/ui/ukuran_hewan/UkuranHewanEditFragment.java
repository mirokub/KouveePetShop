package com.example.kouveepetshop.ui.ukuran_hewan;

import android.app.DatePickerDialog;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.kouveepetshop.api.ApiCustomer;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivities;
import static android.app.PendingIntent.getActivity;

public class UkuranHewanEditFragment extends Fragment {

    private String id_ukuran, pic;
    View myView;
    EditText mUkuranHewan;
    Button mBtnUpdateUkuran;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_ukuran_hewan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateUkuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ukuranHewan = mUkuranHewan.getText().toString();

                if(validate(ukuranHewan)){
                    UkuranHewanModel ukuranHewanModel = new UkuranHewanModel(ukuranHewan , pic);
                    updateUkuranHewan(id_ukuran, ukuranHewanModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mUkuranHewan = myView.findViewById(R.id.etUkuranHewanEdit);
        mBtnUpdateUkuran = myView.findViewById(R.id.btnUpdateUkuranHewan);
    }

    private void setText(){
        Bundle nBundle = getArguments();
        id_ukuran = nBundle.getString("id_ukuran");
        mUkuranHewan.setText(nBundle.getString("ukuran"));
    }

     private boolean validate(String ukuranHewan) {
         if (ukuranHewan == null || ukuranHewan.trim().length() == 0) {
             mUkuranHewan.setError("Ukuran Hewan is required");
             return false;
         }
         return true;
     }

     private void updateUkuranHewan(String id_ukuran, UkuranHewanModel ukuranHewanModel) {
         ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
         Call<ResultOneUkuran> ukuranCall = apiUkuranHewan.updateUkuranHewan(id_ukuran, ukuranHewanModel);

         ukuranCall.enqueue(new Callback<ResultOneUkuran>() {
             @Override
             public void onResponse(Call<ResultOneUkuran> call, Response<ResultOneUkuran> response) {
                 if (response.isSuccessful()) {
                     Toast.makeText(getActivity(), "Update Ukuran Success !", Toast.LENGTH_SHORT).show();
                     FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                     fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new UkuranHewanViewFragment()).commit();
                 } else {
                      Toast.makeText(getActivity(), "Update Ukuran Failed !", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ResultOneUkuran> call, Throwable t) {
                  Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
             }
         });
     }
}
