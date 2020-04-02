package com.example.kouveepetshop.ui.hewan;

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
import com.example.kouveepetshop.api.ApiHewan;
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.hewan.ResultHewan;
import com.example.kouveepetshop.result.hewan.ResultOneHewan;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanEditFragment extends Fragment {

    private String id_hewan, pic;
    View myView;
    EditText mNamaHewan, mTglLahir;
    Spinner mJenisHewan, mUkuranHewan, mCustomer;
    Button mBtnUpdateHewan;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_hewan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();

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

        mBtnUpdateHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaHewan = mNamaHewan.getText().toString();
                String tglLahir = mTglLahir.getText().toString();
                String id_jenis = getJenisSelected();
                String id_ukuran = getUkuranSelected();
                String id_customer = getCustomerSelected();

                if(validate(namaHewan, tglLahir)){
                    HewanModel hewanModel = new HewanModel(namaHewan, tglLahir, id_jenis, id_ukuran, id_customer, pic);
                    updateHewan(id_hewan, hewanModel);
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
        mNamaHewan = myView.findViewById(R.id.etNamaHewanEdit);
        mTglLahir = myView.findViewById(R.id.etTglLahirHewanEdit);
        mJenisHewan = myView.findViewById(R.id.spinnerJenisEdit);
        mUkuranHewan = myView.findViewById(R.id.spinnerUkuranEdit);
        mCustomer = myView.findViewById(R.id.spinnerCustomerEdit);
        mBtnUpdateHewan = myView.findViewById(R.id.btnUpdateHewan);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_hewan = nBundle.getString("id_hewan");
        mNamaHewan.setText(nBundle.getString("nama_hewan"));
        setTglLahir(nBundle.getString("tgl_lahir"));
        setSpinnerJenis(nBundle.getString("jenis_hewan"));
        setSpinnerUkuran(nBundle.getString("ukuran_hewan"));
        setSpinnerCustomer(nBundle.getString("nama_customer"));
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

    public void setSpinnerCustomer(final String customerSelected){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultCustomer> customerCall = apiCustomer.getAllCustomer();

        customerCall.enqueue(new Callback<ResultCustomer>() {
            @Override
            public void onResponse(Call<ResultCustomer> call, Response<ResultCustomer> response) {
                if(response.isSuccessful()){
                    List<CustomerModel> customer = response.body().getListCustomer();
                    ArrayAdapter<CustomerModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, customer);
                    mCustomer.setAdapter(adapter);
                    setCustomerSelected(adapter, customerSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultCustomer> call, Throwable t) {

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

    private void setCustomerSelected(ArrayAdapter<CustomerModel> adapter, String customerSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(customerSelected)){
                mCustomer.setSelection(i);
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

    private String getCustomerSelected(){
        CustomerModel customerModel = (CustomerModel) mCustomer.getSelectedItem();
        return customerModel.getId_customer();
    }

    private boolean validate(String namaHewan, String tglLahir){
        if(namaHewan == null || namaHewan.trim().length() == 0){
            mNamaHewan.setError("Nama Hewan is required");
            return false;
        }
        if(tglLahir == null || tglLahir.trim().length() == 0){
            mTglLahir.setError("Tanggal Lahir is required");
            return false;
        }
        return true;
    }

    private void updateHewan(String id_hewan, HewanModel hewanModel){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultOneHewan> hewanCall = apiHewan.updateHewan(id_hewan, hewanModel);

        hewanCall.enqueue(new Callback<ResultOneHewan>() {
            @Override
            public void onResponse(Call<ResultOneHewan> call, Response<ResultOneHewan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Hewan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new HewanViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Hewan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneHewan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
