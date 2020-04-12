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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiUkuranHewan;
import com.example.kouveepetshop.model.UkuranHewanModel;
import com.example.kouveepetshop.result.ukuran_hewan.ResultOneUkuran;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanViewFragment;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UkuranRecycleAdapter  extends RecyclerView.Adapter<UkuranRecycleAdapter.MyViewHolder> {

    private Context context;
    private List<UkuranHewanModel> result;

    public UkuranRecycleAdapter(FragmentActivity activity, List<UkuranHewanModel> ukuranHewanModelList) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public UkuranRecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_hewan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UkuranRecycleAdapter.MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final UkuranHewanModel UkuranHewanModel = result.get(position);
        holder.mUkuran.setText("Ukuran : Rp" + UkuranHewanModel.getUkuran());
        holder.mEditedBy.setText("Edited by " + UkuranHewanModel.getEdited_by() + " at " + UkuranHewanModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, UkuranHewanModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, UkuranHewanModel UkuranHewanModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        UkuranHewanViewFragment UkuranHewanFragment = new UkuranHewanViewFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("ukuran", UkuranHewanModel.getUkuran());
        UkuranHewanFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, UkuranHewanFragment).commit();
    }

    private void deleteUkuranHewan(final View view, UkuranHewanModel UkuranHewanModel, String pic){
        ApiUkuranHewan ApiUkuranHewan = ApiClient.getClient().create(ApiUkuranHewan.class);
        Call<ResultOneUkuran> UkuranHewanCall;
        UkuranHewanCall = com.example.kouveepetshop.api.ApiUkuranHewan.deleteUkuranHewan(UkuranHewanModel.getId_ukuran(), pic);

        UkuranHewanCall.enqueue(new Callback<ResultOneUkuran>() {
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

    private void showDialog(final View view, final UkuranHewanModel UkuranHewanModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, UkuranHewanModel);
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
                        deleteUkuranHewan(view, UkuranHewanModel, pic);
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
            mUkuran = itemView.findViewById(R.id.txtViewUkuranHewan);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByLayanan);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }
}
