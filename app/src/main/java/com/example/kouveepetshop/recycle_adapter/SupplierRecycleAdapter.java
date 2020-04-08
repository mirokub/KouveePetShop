package com.example.kouveepetshop.recycle_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.kouveepetshop.api.ApiSupplier;
import com.example.kouveepetshop.model.SupplierModel;
import com.example.kouveepetshop.result.supplier.ResultOneSupplier;
import com.example.kouveepetshop.ui.supplier.SupplierEditFragment;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierRecycleAdapter extends RecyclerView.Adapter<SupplierRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<SupplierModel> result;

    public SupplierRecycleAdapter(Context context, List<SupplierModel> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_supplier, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final SupplierModel supplierModel = result.get(position);
        holder.mNamaSupplier.setText(supplierModel.getNama_supplier());
        holder.mAlamatSupplier.setText("Alamat : " + supplierModel.getAlamat());
        holder.mNomorTelpSupplier.setText("Nomor Telepon : " + supplierModel.getNo_telp());
        holder.mEditedBy.setText("Edited by " + supplierModel.getEdited_by() + " at " + supplierModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, supplierModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, SupplierModel supplierModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SupplierEditFragment supplierEditFragment = new SupplierEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_supplier", supplierModel.getId_supplier());
        mBundle.putString("nama_supplier", supplierModel.getNama_supplier());
        mBundle.putString("nomor_telp", supplierModel.getNo_telp());
        supplierEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, supplierEditFragment).commit();
    }

    private void deleteLayanan(final View view, SupplierModel supplierModel, String pic){
        ApiSupplier apiSupplier = ApiClient.getClient().create(ApiSupplier.class);
        Call<ResultOneSupplier> supplierCall = apiSupplier.deleteSupplier(supplierModel.getId_supplier(), pic);

        supplierCall.enqueue(new Callback<ResultOneSupplier>() {
            @Override
            public void onResponse(Call<ResultOneSupplier> call, Response<ResultOneSupplier> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Supplier Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Supplier Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneSupplier> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final SupplierModel supplierModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, supplierModel);
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
                        deleteLayanan(view, supplierModel, pic);
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
        private TextView mNamaSupplier, mAlamatSupplier, mNomorTelpSupplier, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamaSupplier = itemView.findViewById(R.id.txtViewNamaSupplier);
            mAlamatSupplier = itemView.findViewById(R.id.txtViewAlamatSupplier);
            mNomorTelpSupplier = itemView.findViewById(R.id.txtViewNomorTelpSupplier);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedBySupplier);
            mParent = itemView.findViewById(R.id.parentSupplier);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }

}
