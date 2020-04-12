package com.example.kouveepetshop.ui.produk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.recycle_adapter.LayananRecycleAdapter;
import com.example.kouveepetshop.recycle_adapter.ProdukRecycleAdapter;
import com.example.kouveepetshop.result.layanan.ResultLayanan;
import com.example.kouveepetshop.result.produk.ResultProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukViewFragment extends Fragment {

    private List<ProdukModel> produkList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProdukRecycleAdapter produkRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_Produk_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewLayanan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllProduk();

        return myView;
    }

    public void showAllProduk(){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultProduk> resultProdukCall = apiProduk.getAllProduk();

        resultProdukCall.enqueue(new Callback<ResultProduk>() {
            @Override
            public void onResponse(Call<ResultProduk> call, Response<ResultProduk> response) {
                if(response.isSuccessful()){
                    produkList = response.body().getListProduk();
                    produkRecycleAdapter = new ProdukRecycleAdapter(getActivity(), produkList);
                    recyclerView.setAdapter(produkRecycleAdapter);
                    produkRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Produk is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultProduk> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
