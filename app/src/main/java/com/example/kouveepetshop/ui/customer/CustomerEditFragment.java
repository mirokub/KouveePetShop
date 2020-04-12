package com.example.kouveepetshop.ui.customer;

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
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerEditFragment extends Fragment {

    private String id_customer, pic;
    View myView;
    EditText mNamaCustomer, mAlamat, mTglLahir, mNomorTelp;
    Button mBtnUpdateCustomer;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_customer_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        myCalendar = Calendar.getInstance();
        setText();

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

        mBtnUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaCustomer = mNamaCustomer.getText().toString();
                String alamat = mAlamat.getText().toString();
                String tglLahir = mTglLahir.getText().toString();
                String nomorTelp = mNomorTelp.getText().toString();

                if(validate(namaCustomer, tglLahir, nomorTelp)){
                    CustomerModel customerModel = new CustomerModel(namaCustomer, alamat, tglLahir, nomorTelp, pic);
                    updateCustomer(id_customer, customerModel);
                }
            }
        });

        return myView;
    }

    private void updateTglLahir(){
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
        mTglLahir.setText(formatter.format(myCalendar.getTime()));
    }

    private void setAtribut(){
        mNamaCustomer = myView.findViewById(R.id.etNamaCustomerEdit);
        mAlamat = myView.findViewById(R.id.etAlamatCustomerEdit);
        mTglLahir = myView.findViewById(R.id.etTglLahirCustomerEdit);
        mNomorTelp = myView.findViewById(R.id.etNoTelpCustomerEdit);
        mBtnUpdateCustomer = myView.findViewById(R.id.btnUpdateCustomer);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_customer = nBundle.getString("id_customer");
        mNamaCustomer.setText(nBundle.getString("nama_customer"));
        mAlamat.setText(nBundle.getString("alamat_customer"));
        setTglLahir(nBundle.getString("tgl_lahir"));
        mNomorTelp.setText(nBundle.getString("noTelp_customer"));
    }

    public void setTglLahir(String tglLahir){
        mTglLahir.setText(tglLahir);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            myCalendar.setTime(sdf.parse(tglLahir));
            System.out.println(myCalendar.getTime());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private boolean validate(String namaCustomer, String tglLahir, String noTelp){
        if(namaCustomer == null || namaCustomer.trim().length() == 0){
            mNamaCustomer.setError("Nama Customer is required");
            return false;
        }
        if(tglLahir == null || tglLahir.trim().length() == 0){
            mTglLahir.setError("Tanggal Lahir is required");
            return false;
        }
        if(noTelp == null || noTelp.trim().length() == 0){
            mNomorTelp.setError("Nomor Telepon is required");
            return false;
        }
        return true;
    }

    private void updateCustomer(String id_customer, CustomerModel customerModel){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultOneCustomer> customerCall = apiCustomer.updateCustomer(id_customer, customerModel);

        customerCall.enqueue(new Callback<ResultOneCustomer>() {
            @Override
            public void onResponse(Call<ResultOneCustomer> call, Response<ResultOneCustomer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Customer Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new CustomerViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Customer Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneCustomer> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
