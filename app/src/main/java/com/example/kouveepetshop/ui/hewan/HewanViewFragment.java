package com.example.kouveepetshop.ui.hewan;

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
import com.example.kouveepetshop.api.ApiHewan;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.recycle_adapter.HewanRecycleAdapter;
import com.example.kouveepetshop.result.hewan.ResultHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanViewFragment extends Fragment {

    private List<HewanModel> hewanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HewanRecycleAdapter hewanRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_hewan_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewHewan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllHewan();

        return myView;
    }

    public void showAllHewan(){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultHewan> hewanCall = apiHewan.getAllHewan();

        hewanCall.enqueue(new Callback<ResultHewan>() {
            @Override
            public void onResponse(Call<ResultHewan> call, Response<ResultHewan> response) {
                if(response.isSuccessful()){
                    hewanList = response.body().getListHewan();
                    hewanRecycleAdapter = new HewanRecycleAdapter(getActivity(), hewanList);
                    recyclerView.setAdapter(hewanRecycleAdapter);
                    hewanRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Hewan is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultHewan> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}