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
import com.example.kouveepetshop.api.ApiCustomer;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;
import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;
import com.example.kouveepetshop.ui.customer.CustomerEditFragment;
import com.example.kouveepetshop.ui.customer.CustomerViewFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanEditFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanViewFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UkuranHewanRecycleAdapter extends RecyclerView.Adapter<UkuranHewanRecycleAdapter.MyViewHolder> {

    private Context context;
    private List<UkuranHewanModel> result;

    public UkuranHewanRecycleAdapter(Context context, List<UkuranHewanModel> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_ukuran_hewan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final UkuranHewanModel ukuranHewanModel = result.get(position);
        holder.mUkuran.setText(ukuranHewanModel.getUkuran());
        holder.mEditedBy.setText("Edited by " + ukuranHewanModel.getEdited_by() + " at " + ukuranHewanModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, ukuranHewanModel, pic);
            }
        });

    }

    private void moveToEditFragment(View view, UkuranHewanModel ukuranHewanModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        UkuranHewanEditFragment ukuranHewanEditFragment = new UkuranHewanEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_ukuran", ukuranHewanModel.getId_ukuran());
        mBundle.putString("ukuran", ukuranHewanModel.getUkuran());
        ukuranHewanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, ukuranHewanEditFragment).commit();
    }

    private void deleteUkuranHewan(final View view, UkuranHewanModel ukuranHewanModel, String pic){
        ApiUkuranHewan apiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultOneUkuran> ukuranHewanCall = apiUkuranHewan.deleteUkuranHewan(ukuranHewanModel.getId_ukuran(), pic);

        ukuranHewanCall.enqueue(new Callback<ResultOneUkuran>() {
            @Override
            public void onResponse(Call<ResultOneUkuran> call, Response<ResultOneUkuran> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Ukuran Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new UkuranHewanViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Ukuran Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneUkuran> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final UkuranHewanModel ukuranHewanModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, ukuranHewanModel);
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
                        deleteUkuranHewan(view, ukuranHewanModel, pic);
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
        private TextView mUkuran, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mUkuran = itemView.findViewById(R.id.txtViewUkuran);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByUkuranHewan);
            mParent = itemView.findViewById(R.id.parentUkuranHewan);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }
}
