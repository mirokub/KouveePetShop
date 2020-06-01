package com.example.kouveepetshop.recycle_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.api.ApiPenjualanProduk;
import com.example.kouveepetshop.model.DetailPengadaanModel;
import com.example.kouveepetshop.model.DetailPenjualanProdukModel;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.model.PenjualanProdukModel;
import com.example.kouveepetshop.result.pengadaan.ResultOneDetailPengadaan;
import com.example.kouveepetshop.result.pengadaan.ResultOnePengadaan;
import com.example.kouveepetshop.result.penjualan_produk.ResultOneDetailProduk;
import com.example.kouveepetshop.result.penjualan_produk.ResultOnePenjualanProduk;
import com.example.kouveepetshop.ui.transaksiCS.DetailProdukEditFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanProdukViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.DetailPengadaanAddFragment;
import com.example.kouveepetshop.ui.transaksiOwner.DetailPengadaanEditFragment;
import com.example.kouveepetshop.ui.transaksiOwner.DetailPengadaanViewFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengadaanRecycleAdapter extends RecyclerView.Adapter<DetailPengadaanRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private String id, nomor_pengadaan, nama_supplier, tgl_pengadaan, total, status_cetak;
    private List<DetailPengadaanModel> result;
    private List<DetailPengadaanModel> resultFull;
    private PengadaanModel pengadaan = new PengadaanModel();

    public DetailPengadaanRecycleAdapter(Context context, List<DetailPengadaanModel> result,
                                         String id, String nomor_pengadaan, String nama_supplier,
                                         String tgl_pengadaan, String total, String status_cetak) {
        this.context = context;
        this.id = id;
        this.nomor_pengadaan = nomor_pengadaan;
        this.nama_supplier = nama_supplier;
        this.tgl_pengadaan = tgl_pengadaan;
        this.total = total;
        this.status_cetak = status_cetak;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_pengadaan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final DetailPengadaanModel detail = result.get(position);
        holder.mNamaProduk.setText(detail.getNama_produk());
        holder.mSatuan.setText("Satuan : " + detail.getSatuan());
        holder.mHargaProduk.setText("Harga Beli : " + convertCurrency(detail.getHarga_beli()));
        holder.mJumlahPembelian.setText("Jumlah Pengadaan : " + detail.getJumlah() + " " + detail.getSatuan());
        holder.mSubtotal.setText("Subtotal : " + convertCurrency(detail.getSubtotal()));
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status_cetak.equals("Belum Cetak")){
                    showDialog(view, detail);
                }
            }
        });
    }

    private String convertCurrency(String number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(Double.parseDouble(number));
    }

    private void moveToEditFragment(View view, DetailPengadaanModel detailPengadaan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DetailPengadaanEditFragment detailPengadaanEditFragment = new DetailPengadaanEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("nomor_pengadaan", nomor_pengadaan);
        mBundle.putString("nama_supplier", nama_supplier);
        mBundle.putString("tgl_pengadaan", tgl_pengadaan);
        mBundle.putString("total", total);
        mBundle.putString("status_cetak", status_cetak);
        mBundle.putString("id_detail", detailPengadaan.getId_detail());
        mBundle.putString("nama_produk", detailPengadaan.getNama_produk());
        mBundle.putString("jumlah_beli", detailPengadaan.getJumlah());
        mBundle.putString("subtotal", detailPengadaan.getSubtotal());
        detailPengadaanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, detailPengadaanEditFragment).commit();
    }

    private void delete(String idTransaksi, String totalPengeluaran, final View view, DetailPengadaanModel detail){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOnePengadaan> pengadaanCall = apiPengadaan.updateTotal(idTransaksi, String.valueOf(totalPengeluaran));

        pengadaanCall.enqueue(new Callback<ResultOnePengadaan>() {
            @Override
            public void onResponse(Call<ResultOnePengadaan> call, Response<ResultOnePengadaan> response) {
                if(response.isSuccessful()){
                    deleteDetail(view, detail, totalPengeluaran);
                }else{
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultOnePengadaan> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
            }
        });
    }

    private void deleteDetail(final View view, DetailPengadaanModel detail, String totalPengeluaran) {
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultOneDetailPengadaan> detailCall = apiPengadaan.deleteDetail(detail.getId_detail());

        detailCall.enqueue(new Callback<ResultOneDetailPengadaan>() {
            @Override
            public void onResponse(Call<ResultOneDetailPengadaan> call, Response<ResultOneDetailPengadaan> response) {
                if (response.isSuccessful()) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("nomor_pengadaan", nomor_pengadaan);
                    mBundle.putString("nama_supplier", nama_supplier);
                    mBundle.putString("tgl_pengadaan", tgl_pengadaan);
                    mBundle.putString("total", totalPengeluaran);
                    mBundle.putString("status_cetak", status_cetak);
                    DetailPengadaanViewFragment detailPengadaanViewFragment = new DetailPengadaanViewFragment();
                    detailPengadaanViewFragment.setArguments(mBundle);
                    Toast.makeText(context.getApplicationContext(), "Delete Item Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, detailPengadaanViewFragment).commit();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Delete Item Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneDetailPengadaan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final DetailPengadaanModel detailPengadaan){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, detailPengadaan);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                        int subtotal = Integer.parseInt(detailPengadaan.getSubtotal());
                        int totalPengeluaran = Integer.parseInt(total);
                        totalPengeluaran = totalPengeluaran - subtotal;
                        delete(nomor_pengadaan, String.valueOf(totalPengeluaran), view, detailPengadaan);
                    }
                });
                confirmDialog.show();
            }
        });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNamaProduk, mSatuan, mHargaProduk, mJumlahPembelian, mSubtotal;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamaProduk = itemView.findViewById(R.id.tvNamaProdukPengadaan);
            mSatuan = itemView.findViewById(R.id.tvSatuanProdukPengadaan);
            mHargaProduk = itemView.findViewById(R.id.tvHargaProdukPengadaan);
            mJumlahPembelian = itemView.findViewById(R.id.tvJumlahProdukPengadaan);
            mSubtotal = itemView.findViewById(R.id.tvSubtotalProdukPengadaan);
            mParent = itemView.findViewById(R.id.parentDetailPengadaan);
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
            List<DetailPengadaanModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(DetailPengadaanModel detail : resultFull){
                    if(detail.getNama_produk().toLowerCase().contains(filterPattern) ||
                            detail.getSatuan().toLowerCase().contains(filterPattern) ||
                            detail.getHarga_beli().toLowerCase().contains(filterPattern) ||
                            detail.getJumlah().toLowerCase().contains(filterPattern) ||
                            detail.getSubtotal().toLowerCase().contains(filterPattern)){
                        filteredList.add(detail);
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
