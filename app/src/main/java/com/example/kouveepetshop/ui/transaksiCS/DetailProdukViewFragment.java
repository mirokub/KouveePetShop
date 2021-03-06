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
import android.widget.TextView;
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
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.recycle_adapter.DetailProdukRecycleAdapter;
import com.example.kouveepetshop.recycle_adapter.PenjualanProdukRecycleAdapter;
import com.example.kouveepetshop.result.penjualan_produk.ResultDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultPenjualanProduk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukViewFragment extends Fragment {

    private String id, nomor_transaksi, tgl_penjualan, total, status_pembayaran;
    private List<DetailPenjualanProdukModel> details = new ArrayList<>();
    private RecyclerView recyclerView;
    private DetailProdukRecycleAdapter detailProdukRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    TextView mNomorTransaksi, mTglPenjualan, mStatusPembayaran;
    FloatingActionButton fab;

    View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_produk_view, container, false);

        setAtribut();
        setText();

        if(status_pembayaran.equals("Lunas")){
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailProdukAddFragment detailProdukAddFragment = new DetailProdukAddFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("id", id);
                mBundle.putString("nomor_transaksi", nomor_transaksi);
                mBundle.putString("tgl_penjualan", tgl_penjualan);
                mBundle.putString("status_pembayaran", status_pembayaran);
                detailProdukAddFragment.setArguments(mBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailProdukAddFragment).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView = myView.findViewById(R.id.recycleViewDetailProduk);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showAllDetail();

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
                detailProdukRecycleAdapter.getFilter().filter(newText);
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

    public void showAllDetail(){
        ApiPenjualanProduk apiPenjualan = ApiClient.getClient().create(ApiPenjualanProduk.class);
        Call<ResultDetailProduk> detailCall = apiPenjualan.getAllDetail(nomor_transaksi);

        detailCall.enqueue(new Callback<ResultDetailProduk>() {
            @Override
            public void onResponse(Call<ResultDetailProduk> call, Response<ResultDetailProduk> response) {
                if(response.isSuccessful()){
                    details = response.body().getListDetailPenjualanProduk();
                    detailProdukRecycleAdapter = new DetailProdukRecycleAdapter(getActivity(), details, id, nomor_transaksi, tgl_penjualan, total, status_pembayaran);
                    recyclerView.setAdapter(detailProdukRecycleAdapter);
                    detailProdukRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Detail Penjualan Produk is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultDetailProduk> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAtribut(){
        fab = myView.findViewById(R.id.fab_btn_detail_produk);
        mNomorTransaksi = myView.findViewById(R.id.tvNTDetailProduk);
        mTglPenjualan = myView.findViewById(R.id.tvTglPenjualanProdukDetail);
        mStatusPembayaran = myView.findViewById(R.id.tvStatusPembayaranDetail);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        nomor_transaksi = nBundle.getString("nomor_transaksi");
        tgl_penjualan = nBundle.getString("tgl_penjualan");
        total = nBundle.getString("total");
        status_pembayaran = nBundle.getString("status_pembayaran");
        mNomorTransaksi.setText(nomor_transaksi);
        mTglPenjualan.setText("Tanggal Penjualan : " + convertTglPenjualan(tgl_penjualan));
        mStatusPembayaran.setText("Status Pembayaran : " + status_pembayaran);
    }

    private String convertTglPenjualan(String tgl_penjualan){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
        String output = "";
        try{
            Date date = inputFormat.parse(tgl_penjualan);
            output = outputFormat.format(date);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return output;
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