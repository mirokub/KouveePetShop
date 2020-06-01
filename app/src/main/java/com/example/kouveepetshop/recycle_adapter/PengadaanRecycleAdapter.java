package com.example.kouveepetshop.recycle_adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.result.pengadaan.ResultOnePengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultPengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.ui.transaksiCS.DetailProdukViewFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanProdukEditFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanProdukViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.DetailPengadaanViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.PengadaanEditFragment;
import com.example.kouveepetshop.ui.transaksiOwner.PengadaanViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.SuratPemesananFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengadaanRecycleAdapter extends RecyclerView.Adapter<PengadaanRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<PengadaanModel> result;
    private List<PengadaanModel> resultFull;

    public PengadaanRecycleAdapter(Context context, List<PengadaanModel> result) {
        this.context = context;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_pengadaan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final PengadaanModel pengadaan = result.get(position);
        holder.mNomorPengadaan.setText(pengadaan.getNomor_pengadaan());
        holder.mNamaSupplier.setText("Supplier : " + pengadaan.getNama_supplier());
        holder.mTeleponSupplier.setText("Telepon Supplier : " + pengadaan.getNo_telp());
        holder.mAlamatSupplier.setText("Alamat Supplier : " + pengadaan.getAlamat());
        holder.mTglPengadaan.setText("Tanggal Pengadaan : " + convertTglPengadaan(pengadaan.getTgl_pengadaan()));
        holder.mStatusCetak.setText("Status Surat : " + pengadaan.getStatus_cetak_surat());
        holder.mStatusKedatangan.setText("Status Kedatangan : " + pengadaan.getStatus_kedatangan());
        holder.mTotalPengeluaran.setText("Total Pengeluaran : " + convertCurrency(pengadaan.getTotal()));
        holder.mOwner.setText("Edited by " + pengadaan.getOwner());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, pengadaan, pic);
            }
        });
    }

    private String convertCurrency(String number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(Double.parseDouble(number));
    }

    private String convertTglPengadaan(String tgl_penjualan){
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

    private void moveToEditFragment(View view, PengadaanModel pengadaan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        PengadaanEditFragment pengadaanEditFragment = new PengadaanEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id", pengadaan.getId());
        mBundle.putString("id_supplier", pengadaan.getId_supplier());
        pengadaanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, pengadaanEditFragment).commit();
    }

    private void moveToDetailFragment(View view, PengadaanModel pengadaan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DetailPengadaanViewFragment detailPengadaanViewFragment = new DetailPengadaanViewFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id", pengadaan.getId());
        mBundle.putString("nomor_pengadaan", pengadaan.getNomor_pengadaan());
        mBundle.putString("nama_supplier", pengadaan.getNama_supplier());
        mBundle.putString("tgl_pengadaan", pengadaan.getTgl_pengadaan());
        mBundle.putString("total", pengadaan.getTotal());
        mBundle.putString("status_cetak", pengadaan.getStatus_cetak_surat());
        detailPengadaanViewFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, detailPengadaanViewFragment).commit();
    }

    private void deletePengadaan(final View view, PengadaanModel pengadaan, String pic){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOnePengadaan> pengadaanCall = apiPengadaan.deletePengadaan(pengadaan.getId(), pic);

        pengadaanCall.enqueue(new Callback<ResultOnePengadaan>() {
            @Override
            public void onResponse(Call<ResultOnePengadaan> call, Response<ResultOnePengadaan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Pengadaan Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Pengadaan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePengadaan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final PengadaanModel pengadaan, final String pic){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Your Action");
        builder.setItems(new CharSequence[]
                        {"Detail", "Update", "Delete", "Simpan Surat Pemesanan", "Konfirmasi Kedatangan Produk"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                moveToDetailFragment(view, pengadaan);
                                break;
                            case 1:
                                if(pengadaan.getStatus_cetak_surat().equals("Belum Cetak")){
                                    moveToEditFragment(view, pengadaan);
                                }else{
                                    alertDialog("Pengadaan tidak dapat diubah,\n" +
                                            "Surat Pemesanan sudah dicetak !");
                                }
                                break;
                            case 2:
                                if(pengadaan.getStatus_cetak_surat().equals("Belum Cetak")){
                                    showDialogDelete(view, pengadaan, pic);
                                }else{
                                    alertDialog("Pengadaan tidak dapat dibatalkan,\n" +
                                            "Surat Pemesanan sudah dicetak !");
                                }
                                break;
                            case 3:
                                Bundle mBundle = new Bundle();
                                mBundle.putString("id", pengadaan.getId());
                                mBundle.putString("nomor_pengadaan", pengadaan.getNomor_pengadaan());
                                SuratPemesananFragment suratPemesananFragment = new SuratPemesananFragment();
                                suratPemesananFragment.setArguments(mBundle);
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, suratPemesananFragment).commit();
                                break;
                            case 4:
                                if(pengadaan.getStatus_cetak_surat().equals("Belum Cetak")){
                                    alertDialog("Pengadaan belum dapat dikonfirmasi,\n" +
                                            "Surat Pemesanan belum dicetak !");
                                }else if(pengadaan.getStatus_kedatangan().equals("Sudah Datang")){
                                    alertDialog("Pengadaan sudah dikonfirmasi !");
                                }else{
                                    dialogConfirmation(view, pengadaan);
                                }
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void showDialogDelete(View view, PengadaanModel pengadaan, String pic){
        AlertDialog confirmDialog = new AlertDialog.Builder(context).create();
        confirmDialog.setTitle("");
        confirmDialog.setMessage("Are you sure want to delete ?");
        confirmDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePengadaan(view, pengadaan, pic);
            }
        });
        confirmDialog.show();
    }

    private void alertDialog(String message){
        AlertDialog confirmDialog = new AlertDialog.Builder(context).create();
        confirmDialog.setTitle("Information");
        confirmDialog.setMessage(message);
        confirmDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.show();
    }

    private void dialogConfirmation(View view, PengadaanModel pengadaan){
        AlertDialog confirmDialog = new AlertDialog.Builder(context).create();
        confirmDialog.setTitle("");
        confirmDialog.setMessage("Apakah anda yakin akan mengkonfirmasi kedatangan produk ?");
        confirmDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmation(view, pengadaan);
            }
        });
        confirmDialog.show();
    }

    private void confirmation(View view, PengadaanModel pengadaan){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOnePengadaan> pengadaanCall = apiPengadaan.confirmationPengadaan(pengadaan.getId());

        pengadaanCall.enqueue(new Callback<ResultOnePengadaan>() {
            @Override
            public void onResponse(Call<ResultOnePengadaan> call, Response<ResultOnePengadaan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Confirm Pengadaan Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Confirm Pengadaan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePengadaan> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNomorPengadaan, mNamaSupplier, mTeleponSupplier, mAlamatSupplier, mTglPengadaan, mStatusCetak, mStatusKedatangan, mTotalPengeluaran, mOwner;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNomorPengadaan = itemView.findViewById(R.id.txtViewNomorPengadaan);
            mNamaSupplier = itemView.findViewById(R.id.txtViewNamaSupplierPengadaan);
            mTeleponSupplier = itemView.findViewById(R.id.txtViewTeleponSupplier);
            mAlamatSupplier = itemView.findViewById(R.id.txtViewAlamatSupplierPengadaan);
            mTglPengadaan = itemView.findViewById(R.id.txtViewTglPengadaan);
            mStatusCetak = itemView.findViewById(R.id.txtViewStatusCetak);
            mStatusKedatangan = itemView.findViewById(R.id.txtViewStatusKedatangan);
            mTotalPengeluaran = itemView.findViewById(R.id.txtViewTotalPengeluaran);
            mOwner = itemView.findViewById(R.id.txtViewOwnerPengadaan);
            mParent = itemView.findViewById(R.id.parentPengadaan);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PengadaanModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(PengadaanModel pengadaan : resultFull){
                    if(pengadaan.getNomor_pengadaan().toLowerCase().contains(filterPattern) ||
                            pengadaan.getNama_supplier().toLowerCase().contains(filterPattern) ||
                            pengadaan.getNo_telp().toLowerCase().contains(filterPattern) ||
                            pengadaan.getAlamat().toLowerCase().contains(filterPattern) ||
                            pengadaan.getTgl_pengadaan().toLowerCase().contains(filterPattern) ||
                            pengadaan.getStatus_cetak_surat().toLowerCase().contains(filterPattern) ||
                            pengadaan.getStatus_kedatangan().toLowerCase().contains(filterPattern) ||
                            pengadaan.getTotal().toLowerCase().contains(filterPattern) ||
                            pengadaan.getOwner().toLowerCase().contains(filterPattern)){
                        filteredList.add(pengadaan);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            result.clear();
            result.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
