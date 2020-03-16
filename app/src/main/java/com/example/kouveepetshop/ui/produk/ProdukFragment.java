package com.example.kouveepetshop.ui.produk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProdukFragment extends Fragment {

    private ProdukViewModel produkViewModel;
//    RecyclerView recyclerView;
//    Adapter adapter;
//    ArrayList<String> items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        produkViewModel =
                ViewModelProviders.of(this).get(ProdukViewModel.class);

//
//        items = new ArrayList<>();
//        items.add("Produk 1");
//        items.add("Produk 2");
//        items.add("Produk 3");
//
//        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerViewProduk);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, items);
//        recyclerView.setAdapter(adapter);

        View root = inflater.inflate(R.layout.fragment_produk, container, false);
        produkViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        return root;
    }
}