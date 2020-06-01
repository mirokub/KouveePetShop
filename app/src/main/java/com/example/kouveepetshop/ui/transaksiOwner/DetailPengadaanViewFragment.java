package com.example.kouveepetshop.ui.transaksiOwner;

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
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.recycle_adapter.DetailPengadaanRecycleAdapter;
import com.example.kouveepetshop.recycle_adapter.DetailProdukRecycleAdapter;
import com.example.kouveepetshop.result.pengadaan.ResultDetailPengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultDetailProduk;
import com.example.kouveepetshop.ui.transaksiCS.DetailProdukAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengadaanViewFragment extends Fragment {

    private String id, nomor_pengadaan, nama_supplier, tgl_pengadaan, total, status_cetak;
    private List<DetailPengadaanModel> details = new ArrayList<>();
    private RecyclerView recyclerView;
    private DetailPengadaanRecycleAdapter detailPengadaanRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    TextView mNomorPengadaan, mNamaSupplier, mTglPengadaan, mTotal;
    FloatingActionButton fab;

    View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_detail_pengadaan_view, container, false);

        setAtribut();
        setText();

        if(status_cetak.equals("Sudah Cetak")){
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPengadaanAddFragment detailPengadaanAddFragment = new DetailPengadaanAddFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("nomor_pengadaan", nomor_pengadaan);
                mBundle.putString("nama_supplier", nama_supplier);
                mBundle.putString("tgl_pengadaan", tgl_pengadaan);
                mBundle.putString("total", total);
                mBundle.putString("status_cetak", status_cetak);
                detailPengadaanAddFragment.setArguments(mBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, detailPengadaanAddFragment).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView = myView.findViewById(R.id.recycleViewDetailPengadaan);
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
                detailPengadaanRecycleAdapter.getFilter().filter(newText);
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
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultDetailPengadaan> detailCall = apiPengadaan.getAllDetail(nomor_pengadaan);

        detailCall.enqueue(new Callback<ResultDetailPengadaan>() {
            @Override
            public void onResponse(Call<ResultDetailPengadaan> call, Response<ResultDetailPengadaan> response) {
                if(response.isSuccessful()){
                    details = response.body().getListDetailPengadaan();
                    detailPengadaanRecycleAdapter = new DetailPengadaanRecycleAdapter(getActivity(), details, id, nomor_pengadaan, nama_supplier, tgl_pengadaan, total, status_cetak);
                    recyclerView.setAdapter(detailPengadaanRecycleAdapter);
                    detailPengadaanRecycleAdapter.notifyDataSetChanged();
                }else if(response.code() == 404){
                    Toast.makeText(getContext(), "Detail Pengadaan is Empty !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultDetailPengadaan> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAtribut(){
        fab = myView.findViewById(R.id.fab_btn_detail_pengadaan);
        mNomorPengadaan = myView.findViewById(R.id.tvNomorPengadaan);
        mNamaSupplier = myView.findViewById(R.id.tvSupplierPengadaan);
        mTglPengadaan = myView.findViewById(R.id.tvTglPengadaan);
        mTotal = myView.findViewById(R.id.tvTotalPengeluaran);
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        nomor_pengadaan = nBundle.getString("nomor_pengadaan");
        nama_supplier = nBundle.getString("nama_supplier");
        tgl_pengadaan = nBundle.getString("tgl_pengadaan");
        total = nBundle.getString("total");
        status_cetak = nBundle.getString("status_cetak");
        mNomorPengadaan.setText(nomor_pengadaan);
        mNamaSupplier.setText("Nama Supplier : " + nama_supplier);
        mTglPengadaan.setText("Tanggal Pengadaan : " + convertTglPengadaan(tgl_pengadaan));
        mTotal.setText("Total Pengeluaran : " + convertCurrency(total));
    }

    private String convertCurrency(String number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(Double.parseDouble(number));
    }

    private String convertTglPengadaan(String tgl_pengadaan){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
        String output = "";
        try{
            Date date = inputFormat.parse(tgl_pengadaan);
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