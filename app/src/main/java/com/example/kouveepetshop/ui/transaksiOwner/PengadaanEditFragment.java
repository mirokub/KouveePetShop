package com.example.kouveepetshop.ui.transaksiOwner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.customer.ResultCustomer;
import com.example.kouveepetshop.result.hewan.ResultHewan;
import com.example.kouveepetshop.result.pengadaan.ResultOnePengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.result.supplier.ResultSupplier;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanProdukViewFragment;

import java.util.List;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengadaanEditFragment extends Fragment {

    private String id, id_supplier, pic;
    View myView;
    Spinner mPilihSupplier;
    Button mBtnUpdatePengadaan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_pengadaan_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdatePengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_supplier = getSupplierSelected();
                PengadaanModel pengadaan = new PengadaanModel(id_supplier, pic);
                updatePengadaan(id, pengadaan);
            }
        });

        return myView;
    }

    private void setAtribut(){
        mPilihSupplier = myView.findViewById(R.id.spinnerPilihSupplierEdit);
        mBtnUpdatePengadaan = myView.findViewById(R.id.btnUpdatePengadaan);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        id_supplier = nBundle.getString("id_supplier");
        setSpinnerSupplier(id_supplier);
    }

    public void setSpinnerSupplier(final String supplierSelected){
        ApiSupplier apiSupplier = ApiClient.getClient().create(ApiSupplier.class);
        Call<ResultSupplier> supplierCall = apiSupplier.getAllSupplier();

        supplierCall.enqueue(new Callback<ResultSupplier>() {
            @Override
            public void onResponse(Call<ResultSupplier> call, Response<ResultSupplier> response) {
                if(response.isSuccessful()){
                    List<SupplierModel> supplier = response.body().getListSupplier();
                    ArrayAdapter<SupplierModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, supplier);
                    mPilihSupplier.setAdapter(adapter);
                    setSupplierSelected(adapter, supplierSelected);
                }
            }

            @Override
            public void onFailure(Call<ResultSupplier> call, Throwable t) {

            }
        });
    }

    private void setSupplierSelected(ArrayAdapter<SupplierModel> adapter, String supplierSelected){
        for(int i=0; i<adapter.getCount(); i++){
            if(adapter.getItem(i).getId_supplier().equals(supplierSelected)){
                mPilihSupplier.setSelection(i);
                break;
            }
        }
    }

    private String getSupplierSelected(){
        SupplierModel supplierModel = (SupplierModel) mPilihSupplier.getSelectedItem();
        return supplierModel.getId_supplier();
    }

    private void updatePengadaan(String id, PengadaanModel pengadaan){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOnePengadaan> pengadaanCall= apiPengadaan.updatePengadaan(id, pengadaan);

        pengadaanCall.enqueue(new Callback<ResultOnePengadaan>() {
            @Override
            public void onResponse(Call<ResultOnePengadaan> call, Response<ResultOnePengadaan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Pengadaan Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Pengadaan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePengadaan> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
