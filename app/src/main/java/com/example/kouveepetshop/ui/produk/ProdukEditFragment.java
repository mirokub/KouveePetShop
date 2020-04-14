package com.example.kouveepetshop.ui.produk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.produk.ResultOneProduk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukEditFragment extends Fragment {

    private String id_produk, pic;
    View myView;
    EditText mNamaProduk, mSatuan, mHargaJual, mHargaBeli, mStok, mStokMinimum, mGambar;
    Button mBtnUpdateProduk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_produk_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaProduk = mNamaProduk.getText().toString();
                String satuan = mSatuan.getText().toString();
                String hargaJual = mHargaJual.getText().toString();
                String hargaBeli = mHargaBeli.getText().toString();
                String stok = mStok.getText().toString();
                String stok_minimum = mStokMinimum.getText().toString();
                String gambar = mGambar.getText().toString();

                if(validate(namaProduk, satuan, hargaJual, hargaBeli, stok, stok_minimum, gambar)){
                    ProdukModel produkModel = new ProdukModel(namaProduk, satuan, hargaJual, hargaBeli, stok, stok_minimum, gambar, pic);
                    updateProduk(id_produk, produkModel);
                }
            }
        });

        return myView;
    }

    private void setAtribut(){
        mNamaProduk = myView.findViewById(R.id.etNamaProdukEdit);
        mSatuan = myView.findViewById(R.id.etSatuanEdit);
        mHargaJual = myView.findViewById(R.id.etHargaJualEdit);
        mHargaBeli = myView.findViewById(R.id.etHargaBeliEdit);
        mStok = myView.findViewById(R.id.etStokEdit);
        mStokMinimum = myView.findViewById(R.id.etStokMinimumEdit);
        mBtnUpdateProduk = myView.findViewById(R.id.btnUpdateProduk);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_produk = nBundle.getString("id_produk");
        mNamaProduk.setText(nBundle.getString("nama_produk"));
        mSatuan.setText(nBundle.getString("satuan"));
        mHargaJual.setText(nBundle.getString("harga_jual"));
        mHargaBeli.setText(nBundle.getString("harga_beli"));
        mStok.setText(nBundle.getString("stok"));
        mStokMinimum.setText(nBundle.getString("stok_minimum"));
    }

    private boolean validate(String namaProduk, String satuan, String hargaJual, String hargaBeli, String stok, String stok_minimum, String gambar){
        if(namaProduk == null || namaProduk.trim().length() == 0){
            mNamaProduk.setError("Nama Produk is required");
            return false;
        }
        if(satuan == null || satuan.trim().length() == 0){
            mSatuan.setError("Satuan is required");
            return false;
        }
        if(hargaJual == null || hargaJual.trim().length() == 0){
            mHargaJual.setError("Harga Jual is required");
            return false;
        }
        if(hargaBeli == null || hargaBeli.trim().length() == 0){
            mHargaBeli.setError("Harga Beli is required");
            return false;
        }
        if(stok == null || stok.trim().length() == 0){
            mHargaJual.setError("Stok is required");
            return false;
        }
        if(stok_minimum == null || stok_minimum.trim().length() == 0){
            mHargaBeli.setError("Stok minimum is required");
            return false;
        }
        if(gambar == null){
            mHargaBeli.setError("Gambar is required");
            return false;
        }
        return true;
    }

    private void updateProduk(String id_produk, ProdukModel produkModel){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultOneProduk> produkCall = apiProduk.updateProduk(id_produk, produkModel);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Produk Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Produk Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneProduk> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
