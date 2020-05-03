package com.example.kouveepetshop.ui.transaksiCS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.kouveepetshop.api.ApiCustomer;
import com.example.kouveepetshop.api.ApiHewan;
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.hewan.ResultHewan;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanLayananAddFragment extends Fragment {

    private String id_cs;
    View myView;
    Spinner mPilihCustomer, mPilihHewan;
    Button mBtnSavePenjualan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_penjualan_layanan_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        id_cs = SP.getSpId();

        setAtribut();
        setSpinnerCustomer();

        mBtnSavePenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_hewan = getHewanSelected();

//                if(validate(namaSupplier, alamatSupplier, nomorTelpSupplier)){
//                    SupplierModel supplierModel = new SupplierModel(namaSupplier, alamatSupplier, nomorTelpSupplier, pic);
//                    saveSupplier(supplierModel);
//                }
            }
        });

        mPilihCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String id_customer = getCustomerSelected();
//                System.out.println(id_customer);
                setSpinnerHewan(id_customer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return myView;
    }

    private void setAtribut(){
        mPilihCustomer = myView.findViewById(R.id.spinnerPilihCustomer);
        mPilihHewan = myView.findViewById(R.id.spinnerPilihHewan);
        mBtnSavePenjualan = myView.findViewById(R.id.btnSavePenjualanLayanan);
    }

    public void setSpinnerCustomer(){
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

    public void setSpinnerHewan(String id_customer){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultHewan> hewanCall = apiHewan.getHewanByCustomer(id_customer);

        hewanCall.enqueue(new Callback<ResultHewan>() {
            @Override
            public void onResponse(Call<ResultHewan> call, Response<ResultHewan> response) {
                if(response.isSuccessful()){
                    List<HewanModel> hewan = response.body().getListHewan();
                    ArrayAdapter<HewanModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hewan);
                    mPilihHewan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultHewan> call, Throwable t) {

            }
        });
    }

    private String getCustomerSelected(){
        CustomerModel customerModel = (CustomerModel) mPilihCustomer.getSelectedItem();
        return customerModel.getId_customer();
    }

    private String getHewanSelected(){
        HewanModel hewanModel = (HewanModel) mPilihHewan.getSelectedItem();
        return hewanModel.getId_customer();
    }
//
//    private boolean validate(String namaSupplier, String alamatSupplier, String nomorTelpSupplier){
//        if(namaSupplier == null || namaSupplier.trim().length() == 0){
//            mNamaSupplier.setError("Nama Supplier is required");
//            return false;
//        }
//        if(alamatSupplier == null || alamatSupplier.trim().length() == 0){
//            mAlamatSupplier.setError("Alamat is required");
//            return false;
//        }
//        if(nomorTelpSupplier == null || nomorTelpSupplier.trim().length() == 0){
//            mNomorTelpSupplier.setError("Nomor Telepon is required");
//            return false;
//        }
//        return true;
//    }

    private void saveSupplier(SupplierModel supplierModel){
        ApiSupplier apiSupplier = ApiClient.getClient().create(ApiSupplier.class);
        Call<ResultOneSupplier> supplierCall = apiSupplier.createSupplier(supplierModel);

        supplierCall.enqueue(new Callback<ResultOneSupplier>() {
            @Override
            public void onResponse(Call<ResultOneSupplier> call, Response<ResultOneSupplier> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Supplier Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Supplier Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneSupplier> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
