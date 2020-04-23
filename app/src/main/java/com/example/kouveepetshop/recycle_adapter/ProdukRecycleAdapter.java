package com.example.kouveepetshop.recycle_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.produk.ResultOneProduk;
import com.example.kouveepetshop.ui.produk.ProdukViewFragment;
import com.example.kouveepetshop.ui.produk.ProdukEditFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProdukRecycleAdapter extends RecyclerView.Adapter<ProdukRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<ProdukModel> result;
    private List<ProdukModel> resultFull;

    public ProdukRecycleAdapter(Context context, List<ProdukModel> result) {
        this.context = context;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_produk, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final ProdukModel produkModel = result.get(position);

        Picasso.with(context)
                .load("https://tugasbesarkami.com/api/produk/picture/" + produkModel.getGambar())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.mProdukImage);

        holder.mNamaProduk.setText(produkModel.getNama_produk());
        holder.mSatuan.setText("Satuan : " + produkModel.getSatuan());
        holder.mHargaJual.setText("Harga Jual : Rp" + produkModel.getHarga_jual());
        holder.mHargaBeli.setText("Harga Beli : Rp" + produkModel.getHarga_beli());
        holder.mStok.setText("Stok : " + produkModel.getStok());
        holder.mStokMinimum.setText("Stok Minimum : " + produkModel.getStok_minimum());
        holder.mEditedBy.setText("Edited by " + produkModel.getEdited_by() + " at " + produkModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, produkModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, ProdukModel produkModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        ProdukEditFragment produkEditFragment = new ProdukEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_produk", produkModel.getId_produk());
        mBundle.putString("nama_produk", produkModel.getNama_produk());
        mBundle.putString("satuan", produkModel.getSatuan());
        mBundle.putString("harga_jual", produkModel.getHarga_jual());
        mBundle.putString("harga_beli", produkModel.getHarga_beli());
        mBundle.putString("stok", produkModel.getStok());
        mBundle.putString("stok_minimum", produkModel.getStok_minimum());
        mBundle.putString("gambar", produkModel.getGambar());
        produkEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, produkEditFragment).commit();
    }

    private void deleteProduk(final View view, ProdukModel produkModel, String pic){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultOneProduk> produkCall = apiProduk.deleteProduk(produkModel.getId_produk(), pic);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Produk Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Produk Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneProduk> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final ProdukModel produkModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, produkModel);
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
                        deleteProduk(view, produkModel, pic);
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
        private TextView mNamaProduk, mSatuan, mHargaJual, mHargaBeli, mStok, mStokMinimum, mEditedBy;
        private LinearLayout mParent;
        private ImageView mProdukImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mParent = itemView.findViewById(R.id.parentProduk);
            mProdukImage = itemView.findViewById(R.id.gambarProduk);
            mNamaProduk = itemView.findViewById(R.id.txtViewNamaProduk);
            mSatuan = itemView.findViewById(R.id.txtViewSatuanProduk);
            mHargaJual = itemView.findViewById(R.id.txtViewHargaProdukJual);
            mHargaBeli = itemView.findViewById(R.id.txtViewHargaProdukBeli);
            mStok = itemView.findViewById(R.id.txtViewStok);
            mStokMinimum = itemView.findViewById(R.id.txtViewStokMinimum);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByProduk);
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
            List<ProdukModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ProdukModel produk : resultFull){
                    if(produk.getNama_produk().toLowerCase().contains(filterPattern) ||
                        produk.getHarga_beli().contains(constraint) ||
                        produk.getHarga_jual().contains(constraint) ||
                        produk.getStok().contains(constraint) ||
                        produk.getStok_minimum().contains(constraint) ||
                        produk.getSatuan().toLowerCase().contains(filterPattern) ||
                        produk.getEdited_by().toLowerCase().contains(filterPattern)){
                            filteredList.add(produk);
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
