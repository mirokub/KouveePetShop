package com.example.kouveepetshop.ui.supplier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.recycle_adapter.SupplierRecycleAdapter;
import com.example.kouveepetshop.result.supplier.ResultSupplier;
import com.example.kouveepetshop.ui.layanan.LayananAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierViewFragment extends Fragment {

    private List<SupplierModel> supplierList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SupplierRecycleAdapter supplierRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;

    View myView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_supplier_view, container, false);

        setAtribut();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierAddFragment()).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView = myView.findViewById(R.id.recycleViewSupplier);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllSupplier();

        return myView;
    }

    public void showAllSupplier(){
        ApiSupplier apiSupplier = ApiClient.getClient().create(ApiSupplier.class);
        Call<ResultSupplier> supplierCall = apiSupplier.getAllSupplier();

        supplierCall.enqueue(new Callback<ResultSupplier>() {
            @Override
            public void onResponse(Call<ResultSupplier> call, Response<ResultSupplier> response) {
                if(response.isSuccessful()){
                    supplierList = response.body().getListSupplier();
                    supplierRecyclerAdapter = new SupplierRecycleAdapter(getActivity(), supplierList);
                    recyclerView.setAdapter(supplierRecyclerAdapter);
                    supplierRecyclerAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Supplier is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultSupplier> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAtribut(){
        fab = myView.findViewById(R.id.fab_btn_supplier);
    }
}