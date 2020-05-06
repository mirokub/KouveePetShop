package com.example.kouveepetshop.ui.transaksiCS;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.SplashScreen;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.recycle_adapter.PenjualanProdukRecycleAdapter;
import com.example.kouveepetshop.recycle_adapter.SupplierRecycleAdapter;
import com.example.kouveepetshop.result.penjualan_produk.ResultPenjualanProduk;
import com.example.kouveepetshop.result.supplier.ResultSupplier;
import com.example.kouveepetshop.ui.supplier.SupplierAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanProdukViewFragment extends Fragment {

    private List<PenjualanProdukModel> penjualanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PenjualanProdukRecycleAdapter penjualanRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    FloatingActionButton fab;
    RadioGroup mFilterStatus;

    View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_penjualan_produk_view, container, false);

        setAtribut();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new PenjualanProdukAddFragment()).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView = myView.findViewById(R.id.recycleViewPenjualanProduk);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllPenjualan();

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.SearchTxt);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                penjualanRecycleAdapter.getFilter().filter(newText);
                return true;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LogOut:
                showDialog();
                return true;
            default:
                break;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    public void showAllPenjualan(){
        ApiPenjualanProduk apiPenjualan = ApiClient.getClient().create(ApiPenjualanProduk.class);
        Call<ResultPenjualanProduk> penjualanCall = apiPenjualan.getAll();

        penjualanCall.enqueue(new Callback<ResultPenjualanProduk>() {
            @Override
            public void onResponse(Call<ResultPenjualanProduk> call, Response<ResultPenjualanProduk> response) {
                if(response.isSuccessful()){
                    penjualanList = response.body().getListPenjualanProduk();
                    penjualanRecycleAdapter = new PenjualanProdukRecycleAdapter(getActivity(), penjualanList);
                    recyclerView.setAdapter(penjualanRecycleAdapter);
                    penjualanRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Penjualan Produk is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultPenjualanProduk> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAtribut(){
        fab = myView.findViewById(R.id.fab_btn_penjualan_produk);
    }

    private void doLogout(){
        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        SP.spEditor.clear();
        SP.saveSPBoolean(UserSharedPreferences.SP_ISLOGIN, false);
        SP.spEditor.apply();
        Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        getActivity().finish();
        startActivity(intent);
    }

    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure want to logout ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doLogout();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}