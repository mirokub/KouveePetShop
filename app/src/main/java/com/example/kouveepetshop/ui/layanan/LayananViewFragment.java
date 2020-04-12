package com.example.kouveepetshop.ui.layanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.recycle_adapter.LayananRecycleAdapter;
import com.example.kouveepetshop.result.layanan.ResultLayanan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananViewFragment extends Fragment {

    private List<LayananModel> layananList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LayananRecycleAdapter layananRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_layanan_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewLayanan);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllLayanan();

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.SearchTxt);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                layananRecycleAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }



    public void showAllLayanan(){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultLayanan> resultLayananCall = apiLayanan.getAllLayanan();

       resultLayananCall.enqueue(new Callback<ResultLayanan>() {
           @Override
           public void onResponse(Call<ResultLayanan> call, Response<ResultLayanan> response) {
               if(response.isSuccessful()){
                   layananList = response.body().getListLayanan();
                   layananRecycleAdapter = new LayananRecycleAdapter(getActivity(), layananList);
                   recyclerView.setAdapter(layananRecycleAdapter);
                   layananRecycleAdapter.notifyDataSetChanged();
               }else if(response.code() == 404){
                   Toast.makeText(getContext(), "Layanan is Empty !", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResultLayanan> call, Throwable t) {
               System.out.println(t.getMessage());
               Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
           }
       });
    }
}