package com.example.kouveepetshop.ui.jenis_hewan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.recycle_adapter.JenisHewanRecycleAdapter;
import com.example.kouveepetshop.result.jenis_hewan.ResultJenisHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisHewanViewFragment extends Fragment {

//    private JenisHewanViewModel jenisHewanViewModel;

    private List<JenisHewanModel> jenisHewanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private JenisHewanRecycleAdapter jenisHewanRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_jenis_hewan_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewJenisHewan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllJenis();

        return myView;

//        jenisHewanViewModel = ViewModelProviders.of(this).get(JenisHewanViewModel.class);
//
//        View root = inflater.inflate(R.layout.fragment_jenis_hewan_view, container, false);
//
//        final TextView textView = root.findViewById(R.id.text_jenis_hewan);
//        jenisHewanViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
    }

    public void showAllJenis(){
        ApiJenisHewan apiJenisHewan = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultJenisHewan> resultJenisHewanCall = apiJenisHewan.getAllJenisHewan();

        resultJenisHewanCall.enqueue(new Callback<ResultJenisHewan>() {
            @Override
            public void onResponse(Call<ResultJenisHewan> call, Response<ResultJenisHewan> response) {
                if(response.isSuccessful()){
                    jenisHewanList = response.body().getJenisHewanModels();
                    jenisHewanRecycleAdapter = new JenisHewanRecycleAdapter(getActivity(), jenisHewanList);
                    recyclerView.setAdapter(jenisHewanRecycleAdapter);
                    jenisHewanRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Jenis Hewan is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultJenisHewan> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}