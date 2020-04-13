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
import com.example.kouveepetshop.api.ApiLayanan;
import com.example.kouveepetshop.model.LayananModel;
import com.example.kouveepetshop.result.layanan.ResultOneLayanan;
import com.example.kouveepetshop.ui.layanan.LayananEditFragment;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananRecycleAdapter extends RecyclerView.Adapter<LayananRecycleAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<LayananModel> result;
    private List<LayananModel> resultFull;

    public LayananRecycleAdapter(Context context, List<LayananModel> result) {
        this.context = context;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_layanan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final LayananModel layananModel = result.get(position);
        holder.mNamaLayanan.setText(layananModel.getNama_layanan() + " " + layananModel.getJenis() + " " +layananModel.getUkuran());
        holder.mHarga.setText("Harga : Rp" + layananModel.getHarga());
        holder.mEditedBy.setText("Edited by " + layananModel.getEdited_by() + " at " + layananModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, layananModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, LayananModel layananModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        LayananEditFragment layananEditFragment = new LayananEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_layanan", layananModel.getId_layanan());
        mBundle.putString("nama_layanan", layananModel.getNama_layanan());
        mBundle.putString("jenis_hewan", layananModel.getJenis());
        mBundle.putString("ukuran_hewan", layananModel.getUkuran());
        mBundle.putString("harga", layananModel.getHarga());
        layananEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, layananEditFragment).commit();
    }

    private void deleteLayanan(final View view, LayananModel layananModel, String pic){
        ApiLayanan apiLayanan = ApiClient.getClient().create(ApiLayanan.class);
        Call<ResultOneLayanan> layananCall = apiLayanan.deleteLayanan(layananModel.getId_layanan(), pic);

        layananCall.enqueue(new Callback<ResultOneLayanan>() {
            @Override
            public void onResponse(Call<ResultOneLayanan> call, Response<ResultOneLayanan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Layanan Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new LayananViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Layanan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneLayanan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final LayananModel layananModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, layananModel);
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
                        deleteLayanan(view, layananModel, pic);
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
        private TextView mNamaLayanan, mHarga, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mParent = itemView.findViewById(R.id.parentLayanan);
            mNamaLayanan = itemView.findViewById(R.id.txtViewNamaLayanan);
            mHarga = itemView.findViewById(R.id.txtViewHargaLayanan);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByLayanan);
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
            List<LayananModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(LayananModel layanan : resultFull){
                    if(layanan.getNama_layanan().toLowerCase().contains(filterPattern) ||
                        layanan.getJenis().toLowerCase().contains(filterPattern) ||
                        layanan.getUkuran().toLowerCase().contains(filterPattern) ||
                        layanan.getHarga().contains(constraint) ||
                        layanan.getEdited_by().toLowerCase().contains(filterPattern)){
                            filteredList.add(layanan);
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
