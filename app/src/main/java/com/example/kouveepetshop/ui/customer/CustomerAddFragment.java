package com.example.kouveepetshop.ui.customer;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiCustomer;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;
import com.example.kouveepetshop.ui.customer.CustomerViewFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddFragment extends Fragment {

    private String pic;
    View myView;
    EditText mNamaCustomer, mAlamat, mTglLahir, mNomorTelp;
    Button mBtnSaveCustomer;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_customer_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTglLahir();
            }
        };

        mTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mBtnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaCustomer = mNamaCustomer.getText().toString();
                String alamat = mAlamat.getText().toString();
                String tglLahir = mTglLahir.getText().toString();
                String nomorTelp = mNomorTelp.getText().toString();

                if(validate(namaCustomer, alamat, tglLahir, nomorTelp)){
                    CustomerModel customerModel = new CustomerModel(namaCustomer, alamat, tglLahir, nomorTelp, pic);
                    saveCustomer(customerModel);
                }
            }
        });

        return myView;
    }

    private void updateTglLahir(){
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
        mTglLahir.setText(formatter.format(myCalendar.getTime()));
        System.out.println(myCalendar.getTime());
    }

    private void setAtribut(){
        mNamaCustomer = myView.findViewById(R.id.etNamaCustomer);
        mAlamat = myView.findViewById(R.id.etAlamatCustomer);
        mTglLahir = myView.findViewById(R.id.etTglLahirCustomer);
        mBtnSaveCustomer = myView.findViewById(R.id.btnSaveCustomer);
    }

    private boolean validate(String namaCustomer, String alamat, String tglLahir, String nomorTelp){
        if(namaCustomer == null || namaCustomer.trim().length() == 0){
            mNamaCustomer.setError("Nama Customer is required");
            return false;
        }
        if(tglLahir == null || tglLahir.trim().length() == 0){
            mTglLahir.setError("Tanggal Lahir is required");
            return false;
        }
        return true;
    }

    private void saveCustomer(CustomerModel customerModel){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultOneCustomer> customerCall = apiCustomer.createCustomer(customerModel);

        customerCall.enqueue(new Callback<ResultOneCustomer>() {
            @Override
            public void onResponse(Call<ResultOneCustomer> call, Response<ResultOneCustomer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Customer Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new CustomerViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Customer Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneCustomer> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
