package com.example.kouveepetshop.ui.transaksiCS;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.kouveepetshop.api.ApiPenjualanLayanan;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.hewan.ResultHewan;
import com.example.kouveepetshop.result.hewan.ResultOneHewan;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;
import com.example.kouveepetshop.ui.hewan.HewanViewFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanLayananEditFragment extends Fragment {

    private String id, id_customer, id_hewan, id_cs;
    View myView;
    Spinner mPilihCustomer, mPilihHewan;
    RadioGroup mTipeCustomer;
    RadioButton mGuest, mMember;
    Button mBtnUpdatePenjualan;
    TextView mCustomer, mHewan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_penjualan_layanan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        id_cs = SP.getSpId();

        setAtribut();
        setText();

        mTipeCustomer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = mTipeCustomer.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radioButtonGuestEdit:
                        mCustomer.setVisibility(View.GONE);
                        mHewan.setVisibility(View.GONE);
                        mPilihCustomer.setVisibility(View.GONE);
                        mPilihHewan.setVisibility(View.GONE);
                        break;

                    case R.id.radioButtonMemberEdit:
                        mCustomer.setVisibility(View.VISIBLE);
                        mHewan.setVisibility(View.VISIBLE);
                        mPilihCustomer.setVisibility(View.VISIBLE);
                        mPilihHewan.setVisibility(View.VISIBLE);
                        setSpinnerCustomerOnCreate();
                        break;
                }
            }
        });

        mPilihCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String id_customer = getCustomerSelected();
                setSpinnerHewan(id_customer, id_hewan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnUpdatePenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadio = mTipeCustomer.getCheckedRadioButtonId();
                switch (idRadio){
                    case R.id.radioButtonGuestEdit:
                        PenjualanLayananModel penjualan = new PenjualanLayananModel(id_cs);
                        updatePenjualan(id, penjualan);
                    break;

                    case R.id.radioButtonMemberEdit:
                        String id_hewan = getHewanSelected();
                        PenjualanLayananModel penjualanLayanan = new PenjualanLayananModel(id_hewan, id_cs);
                        updatePenjualan(id, penjualanLayanan);
                    break;
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mTipeCustomer = myView.findViewById(R.id.radioGroupCustomerEdit);
        mGuest = myView.findViewById(R.id.radioButtonGuestEdit);
        mMember = myView.findViewById(R.id.radioButtonMemberEdit);
        mPilihCustomer = myView.findViewById(R.id.spinnerPilihCustomerEdit);
        mPilihHewan = myView.findViewById(R.id.spinnerPilihHewanEdit);
        mCustomer = myView.findViewById(R.id.tvPilihCustomerEdit);
        mHewan = myView.findViewById(R.id.tvPilihHewanEdit);
        mBtnUpdatePenjualan = myView.findViewById(R.id.btnUpdatePenjualanLayanan);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        id_customer = nBundle.getString("id_customer");
        id_hewan = nBundle.getString("id_hewan");
        if (id_hewan == null) {
            mCustomer.setVisibility(View.GONE);
            mHewan.setVisibility(View.GONE);
            mPilihCustomer.setVisibility(View.GONE);
            mPilihHewan.setVisibility(View.GONE);
            mGuest.setChecked(true);
        }else{
            mCustomer.setVisibility(View.VISIBLE);
            mHewan.setVisibility(View.VISIBLE);
            mPilihCustomer.setVisibility(View.VISIBLE);
            mPilihHewan.setVisibility(View.VISIBLE);
            setSpinnerCustomer(id_customer);
            mMember.setChecked(true);
        }
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
                    mPilihCustomer.setAdapter(adapter);
                    setCustomerSelected(adapter, customerSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultCustomer> call, Throwable t) {

            }
        });
    }

    public void setSpinnerCustomerOnCreate(){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultCustomer> customerCall = apiCustomer.getAllCustomer();

        customerCall.enqueue(new Callback<ResultCustomer>() {
            @Override
            public void onResponse(Call<ResultCustomer> call, Response<ResultCustomer> response) {
                if(response.isSuccessful()){
                    List<CustomerModel> customer = response.body().getListCustomer();
                    ArrayAdapter<CustomerModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, customer);
                    mPilihCustomer.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultCustomer> call, Throwable t) {

            }
        });
    }

    public void setSpinnerHewan(String id_customer, String id_hewan){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultHewan> hewanCall = apiHewan.getHewanByCustomer(id_customer);

        hewanCall.enqueue(new Callback<ResultHewan>() {
            @Override
            public void onResponse(Call<ResultHewan> call, Response<ResultHewan> response) {
                if(response.isSuccessful()){
                    List<HewanModel> hewan = response.body().getListHewan();
                    ArrayAdapter<HewanModel> adapter = new ArrayAdapter<HewanModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hewan);
                    mPilihHewan.setAdapter(adapter);
                    setHewanSelected(adapter, id_hewan);
                }
            }

            @Override
            public void onFailure(Call<ResultHewan> call, Throwable t) {

            }
        });
    }

    private void setCustomerSelected(ArrayAdapter<CustomerModel> adapter, String customerSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).getId_customer().equals(customerSelected)){
                mPilihCustomer.setSelection(i);
                break;
            }
        }
    }

    private void setHewanSelected(ArrayAdapter<HewanModel> adapter, String hewanSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).getId_hewan().equals(hewanSelected)){
                mPilihHewan.setSelection(i);
                break;
            }
        }
    }

    private String getHewanSelected(){
        HewanModel hewanModel = (HewanModel) mPilihHewan.getSelectedItem();
        return hewanModel.getId_hewan();
    }

    private String getCustomerSelected(){
        CustomerModel customerModel = (CustomerModel) mPilihCustomer.getSelectedItem();
        return customerModel.getId_customer();
    }

    private void updatePenjualan(String id, PenjualanLayananModel penjualan){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.updatePenjualanLayanan(id, penjualan);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Penjualan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new PenjualanLayananViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Penjualan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanLayanan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
