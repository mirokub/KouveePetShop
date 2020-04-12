package com.example.kouveepetshop.ui.ukuran_hewan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.recycle_adapter.UkuranRecycleAdapter;
import com.example.kouveepetshop.result.ukuran_hewan.ResultUkuranHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UkuranHewanViewFragment extends Fragment {

    private List<UkuranHewanModel> ukuranHewanModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UkuranRecycleAdapter ukuranRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_ukuran_hewan_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewUkuranHewan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllUkuranHewan();

        return myView;
    }

    public void showAllUkuranHewan(){
        ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultUkuranHewan> ukuranHewanCall = apiUkuranHewan.getAllUkuranHewan();

        ukuranHewanCall.enqueue(new Callback<ResultUkuranHewan>() {
            @Override
            public void onResponse(Call<ResultUkuranHewan> call, Response<ResultUkuranHewan> response) {
                if(response.isSuccessful()){
                    ukuranHewanModelList = response.body().getUkuranHewanModels();
                    ukuranRecyclerAdapter = new UkuranRecycleAdapter(getActivity(), ukuranHewanModelList);
                    recyclerView.setAdapter(ukuranRecyclerAdapter);
                    ukuranRecyclerAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Ukuran Hewan is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultUkuranHewan> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.SearchTxt);
        SearchView searchView = new SearchView(getActivity());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchItem.setActionView(searchView);
    }
}