package com.example.kouveepetshop.ui.supplier;

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
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.supplier.ResultSupplier;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierEditFragment extends Fragment {

    private String id_supplier, pic;
    View myView;
    EditText mNamaSupplier, mAlamatSupplier, mNomorTelpSupplier;
    Button mBtnUpdateSupplier;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_supplier_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();

        mBtnUpdateSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaSupplier = mNamaSupplier.getText().toString();
                String alamatSupplier = mAlamatSupplier.getText().toString();
                String nomorTelpSupplier = mNomorTelpSupplier.getText().toString();

                if(validate(namaSupplier, alamatSupplier, nomorTelpSupplier)){
                    SupplierModel supplierModel = new SupplierModel(namaSupplier, alamatSupplier, nomorTelpSupplier, pic);
                    updateSupplier(id_supplier, supplierModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mNamaSupplier = myView.findViewById(R.id.etNamaSupplierEdit);
        mAlamatSupplier = myView.findViewById(R.id.etAlamatSupplierEdit);
        mNomorTelpSupplier = myView.findViewById(R.id.etNoTelpSupplierEdit);
        mBtnUpdateSupplier = myView.findViewById(R.id.btnUpdateSupplier);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_supplier = nBundle.getString("id_supplier");
        mNamaSupplier.setText(nBundle.getString("nama_supplier"));
        mAlamatSupplier.setText(nBundle.getString("alamat_supplier"));
        mNomorTelpSupplier.setText(nBundle.getString("noTelp_supplier"));
    }

    private boolean validate(String namaSupplier, String alamatSupplier, String nomorTelpSupplier){
        if(namaSupplier == null || namaSupplier.trim().length() == 0){
            mNamaSupplier.setError("Nama Supplier is required");
            return false;
        }
        if(alamatSupplier == null || alamatSupplier.trim().length() == 0){
            mAlamatSupplier.setError("Alamat is required");
            return false;
        }
        if(nomorTelpSupplier == null || nomorTelpSupplier.trim().length() == 0){
            mNomorTelpSupplier.setError("Nomor Telepon is required");
            return false;
        }
        return true;
    }

    private void updateSupplier(String id_supplier, SupplierModel supplierModel){
        ApiSupplier apiSupplier = ApiClient.getClient().create(ApiSupplier.class);
        Call<ResultOneSupplier> supplierCall = apiSupplier.updateSupplier(id_supplier, supplierModel);

        supplierCall.enqueue(new Callback<ResultOneSupplier>() {
            @Override
            public void onResponse(Call<ResultOneSupplier> call, Response<ResultOneSupplier> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Supplier Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Supplier Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneSupplier> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
