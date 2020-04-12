package com.example.kouveepetshop.ui.customer;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiCustomer;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.recycle_adapter.CustomerRecyclerAdapter;
import com.example.kouveepetshop.result.customer.ResultCustomer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewFragment extends Fragment {

    private List<CustomerModel> customerList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomerRecyclerAdapter customerRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    View myView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_customer_view, container, false);

        recyclerView = myView.findViewById(R.id.recycleViewCustomer);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllCustomer();

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
                customerRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void showAllCustomer(){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultCustomer> customerCall = apiCustomer.getAllCustomer();

        customerCall.enqueue(new Callback<ResultCustomer>() {
            @Override
            public void onResponse(Call<ResultCustomer> call, Response<ResultCustomer> response) {
                if(response.isSuccessful()){
                    customerList = response.body().getListCustomer();
                    customerRecyclerAdapter = new CustomerRecyclerAdapter(getActivity(), customerList);
                    recyclerView.setAdapter(customerRecyclerAdapter);
                    customerRecyclerAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Customer is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultCustomer> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

}