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
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.PenjualanLayananModel;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.penjualan_layanan.ResultOnePenjualanLayanan;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;
import com.example.kouveepetshop.ui.supplier.SupplierEditFragment;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;
import com.example.kouveepetshop.ui.transaksiCS.DetailLayananViewFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanLayananEditFragment;
import com.example.kouveepetshop.ui.transaksiCS.PenjualanLayananViewFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanLayananRecycleAdapter extends RecyclerView.Adapter<PenjualanLayananRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<PenjualanLayananModel> result;
    private List<PenjualanLayananModel> resultFull;

    public PenjualanLayananRecycleAdapter(Context context, List<PenjualanLayananModel> result) {
        this.context = context;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_penjualan_layanan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String id_cs = SP.getSpId();
        final PenjualanLayananModel penjualan = result.get(position);
        holder.mNomorTransaksi.setText(penjualan.getNomor_transaksi());
        if(penjualan.getNama_customer() == null){
            holder.mNamaCustomer.setText("Customer : Guest");
        }else{
            holder.mNamaCustomer.setText("Customer : " + penjualan.getNama_customer());
        }
        if(penjualan.getNama_hewan() == null){
            holder.mNamaHewan.setText("Hewan : Guest");
        }else{
            holder.mNamaHewan.setText("Hewan : " + penjualan.getNama_hewan() + " (" + penjualan.getJenis() + " - " + penjualan.getUkuran() + ")");
        }
        holder.mTglPenjualan.setText("Tanggal Penjualan : " + convertTglPenjualan(penjualan.getTgl_penjualan()));
        holder.mStatusLayanan.setText("Status Layanan : " + penjualan.getStatus_layanan());
        holder.mStatusPembayaran.setText("Status Pembayaran : " + penjualan.getStatus_pembayaran());
        holder.mTotalBiaya.setText("Total Biaya : Rp " + penjualan.getTotal());
        holder.mCustomerService.setText("Customer Service : " + penjualan.getCustomer_service());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, penjualan, id_cs);
            }
        });
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

    private void moveToEditFragment(View view, PenjualanLayananModel penjualan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        PenjualanLayananEditFragment penjualanEditFragment = new PenjualanLayananEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id", penjualan.getId());
        mBundle.putString("id_customer", penjualan.getId_customer());
        mBundle.putString("id_hewan", penjualan.getId_hewan());
        penjualanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, penjualanEditFragment).commit();
    }

    private void moveToDetailFragment(View view, PenjualanLayananModel penjualan){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        DetailLayananViewFragment detailLayananViewFragment = new DetailLayananViewFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id", penjualan.getId());
        mBundle.putString("nomor_transaksi", penjualan.getNomor_transaksi());
        mBundle.putString("tgl_penjualan", penjualan.getTgl_penjualan());
        mBundle.putString("total", penjualan.getTotal());
        mBundle.putString("status_pembayaran", penjualan.getStatus_pembayaran());
        detailLayananViewFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, detailLayananViewFragment).commit();
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

    private void showDialog(final View view, final PenjualanLayananModel penjualan, final String id_cs){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Your Action");
        builder.setItems(new CharSequence[]
                        {"Detail", "Update", "Change Status", "Delete"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                moveToDetailFragment(view, penjualan);
                                break;
                            case 1:
                                moveToEditFragment(view, penjualan);
                                break;
                            case 2:
                                showDialogChangeStatus(view, penjualan, id_cs);
                                break;
                            case 3:
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
                                        deletePenjualanLayanan(view, penjualan, id_cs);
                                    }
                                });
                                confirmDialog.show();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void showDialogChangeStatus(final View view, final PenjualanLayananModel penjualan, final String id_cs){
        AlertDialog changeStatusDialog = new AlertDialog.Builder(context).create();
        changeStatusDialog.setTitle("");
        changeStatusDialog.setMessage("Are you sure want to change the status layanan ?");
        changeStatusDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        changeStatusDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ubahStatus(view, penjualan, id_cs);
            }
        });
        changeStatusDialog.show();
    }

    private void ubahStatus(final View view, final PenjualanLayananModel penjualan, final String id_cs){
        ApiPenjualanLayanan apiPenjualan = ApiClient.getClient().create(ApiPenjualanLayanan.class);
        Call<ResultOnePenjualanLayanan> penjualanCall = apiPenjualan.changeStatus(penjualan.getId(), "Selesai", id_cs);

        penjualanCall.enqueue(new Callback<ResultOnePenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResultOnePenjualanLayanan> call, Response<ResultOnePenjualanLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Change Status Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new PenjualanLayananViewFragment()).commit();
                }else{
                    System.out.println(response.code());
                    Toast.makeText(context.getApplicationContext(), "Change Status Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOnePenjualanLayanan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNomorTransaksi, mNamaCustomer, mNamaHewan, mTglPenjualan, mStatusLayanan, mStatusPembayaran, mTotalBiaya, mCustomerService;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNomorTransaksi = itemView.findViewById(R.id.txtViewNTLayanan);
            mNamaCustomer = itemView.findViewById(R.id.txtViewCustomerLayanan);
            mNamaHewan = itemView.findViewById(R.id.txtViewHewanLayanan);
            mTglPenjualan = itemView.findViewById(R.id.txtViewTglPenjualanLayanan);
            mStatusLayanan = itemView.findViewById(R.id.txtViewStatusLayanan);
            mStatusPembayaran = itemView.findViewById(R.id.txtViewStatusPembayaranLayanan);
            mTotalBiaya = itemView.findViewById(R.id.txtViewTotalBiayaLayanan);
            mCustomerService = itemView.findViewById(R.id.txtViewCSLayanan);
            mParent = itemView.findViewById(R.id.parentPenjualanLayanan);
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
            List<PenjualanLayananModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(PenjualanLayananModel penjualan : resultFull){
                    if(penjualan.getNomor_transaksi().toLowerCase().contains(filterPattern) ||
//                            penjualan.getNama_customer().toLowerCase().contains(filterPattern) ||
//                            penjualan.getNama_hewan().toLowerCase().contains(filterPattern) ||
                            penjualan.getTgl_penjualan().toLowerCase().contains(filterPattern) ||
                            penjualan.getStatus_layanan().toLowerCase().equals(filterPattern) ||
                            penjualan.getStatus_pembayaran().toLowerCase().contains(filterPattern) ||
                            penjualan.getTotal().toLowerCase().contains(filterPattern) ||
                            penjualan.getCustomer_service().toLowerCase().contains(filterPattern)){
                        filteredList.add(penjualan);
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
