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
import com.example.kouveepetshop.api.ApiPenjualanLayanan;
import com.example.kouveepetshop.model.DetailPenjualanLayananModel;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.ui.transaksiCS.DetailLayananEditFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanLayananEditFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanLayananViewFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLayananRecycleAdapter extends RecyclerView.Adapter<DetailLayananRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private String id, nomor_transaksi, tgl_penjualan, status_pembayaran;
    private List<DetailPenjualanLayananModel> result;
    private List<DetailPenjualanLayananModel> resultFull;

    public DetailLayananRecycleAdapter(Context context, List<DetailPenjualanLayananModel> result,
                                       String id, String nomor_transaksi, String tgl_penjualan, String status_pembayaran) {
        this.context = context;
        this.id = id;
        this.nomor_transaksi = nomor_transaksi;
        this.tgl_penjualan = tgl_penjualan;
        this.status_pembayaran = status_pembayaran;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_layanan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final DetailPenjualanLayananModel penjualan = result.get(position);
        holder.mNamaLayanan.setText(penjualan.getNama_layanan());
        holder.mHargaLayanan.setText("Harga Layanan : Rp " + penjualan.getHarga_layanan());
        holder.mJumlahPembelian.setText("Jumlah Pembelian : " + penjualan.getJumlah());
        holder.mSubtotal.setText("Subtotal : Rp " + penjualan.getSubtotal());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status_pembayaran.equals("Belum Lunas")){
                    showDialog(view, penjualan);
                }
            }
        });
    }

    private void moveToEditFragment(View view, DetailPenjualanLayananModel detailPenjualan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DetailLayananEditFragment detailLayananEditFragment = new DetailLayananEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id", id);
        mBundle.putString("nomor_transaksi", nomor_transaksi);
        mBundle.putString("tgl_penjualan", tgl_penjualan);
        mBundle.putString("status_pembayaran", status_pembayaran);
        mBundle.putString("id_detail", detailPenjualan.getId_detail());
        mBundle.putString("nama_layanan", detailPenjualan.getNama_layanan());
        mBundle.putString("jumlah_beli", detailPenjualan.getJumlah());
        mBundle.putString("subtotal", detailPenjualan.getSubtotal());
        detailLayananEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailLayananEditFragment).commit();
    }

    private void deletePenjualanLayanan(final View view, PenjualanLayananModel penjualan, String id_cs){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.deletePenjualanLayanan(penjualan.getId(), id_cs);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Penjualan Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new PenjualanLayananViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Penjualan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanLayanan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final DetailPenjualanLayananModel detailPenjualan){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, detailPenjualan);
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
//                        deletePenjualanLayanan(view, penjualan, id_cs);
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
        private TextView mNamaLayanan, mHargaLayanan, mJumlahPembelian, mSubtotal;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamaLayanan = itemView.findViewById(R.id.tvNamaLayanan);
            mHargaLayanan = itemView.findViewById(R.id.tvHargaLayanan);
            mJumlahPembelian = itemView.findViewById(R.id.tvJumlahBeliLayanan);
            mSubtotal = itemView.findViewById(R.id.tvSubtotalLayanan);
            mParent = itemView.findViewById(R.id.parentDetailLayanan);
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
//            List<PenjualanLayananModel> filteredList = new ArrayList<>();
//
//            if(constraint == null || constraint.length() == 0){
//                filteredList.addAll(resultFull);
//            }else{
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for(PenjualanLayananModel penjualan : resultFull){
//                    if(penjualan.getNomor_transaksi().toLowerCase().contains(filterPattern) ||
////                            penjualan.getNama_customer().toLowerCase().contains(filterPattern) ||
////                            penjualan.getNama_hewan().toLowerCase().contains(filterPattern) ||
//                            penjualan.getTgl_penjualan().toLowerCase().contains(filterPattern) ||
//                            penjualan.getStatus_layanan().toLowerCase().equals(filterPattern) ||
//                            penjualan.getTotal().toLowerCase().contains(filterPattern) ||
//                            penjualan.getCustomer_service().toLowerCase().contains(filterPattern)){
//                        filteredList.add(penjualan);
//                    }
//                }
//            }
//
            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
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
