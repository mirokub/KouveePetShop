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
import com.example.kouveepetshop.api.ApiJenisHewan;
import com.example.kouveepetshop.model.JenisHewanModel;
import com.example.kouveepetshop.result.jenis_hewan.ResultOneJenis;
import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanEditFragment;
import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanViewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisHewanRecycleAdapter extends RecyclerView.Adapter<JenisHewanRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<JenisHewanModel> result;

    public JenisHewanRecycleAdapter(Context context, List<JenisHewanModel> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_jenis_hewan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final JenisHewanModel jenisHewanModel = result.get(position);
        holder.mJenis.setText(jenisHewanModel.getJenis());
        holder.mEditedBy.setText("Edited by " + jenisHewanModel.getEdited_by() + " at " + jenisHewanModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, jenisHewanModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, JenisHewanModel jenisHewanModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        JenisHewanEditFragment jenisHewanEditFragment = new JenisHewanEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_jenis", jenisHewanModel.getId_jenis());
        mBundle.putString("jenis", jenisHewanModel.getJenis());
        jenisHewanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, jenisHewanEditFragment).commit();
    }

    private void deleteJenis(final View view, JenisHewanModel jenisHewanModel, String pic){
        ApiJenisHewan apiJenisHewan = ApiClient.getClient().create(ApiJenisHewan.class);
        Call<ResultOneJenis> jenisCall = apiJenisHewan.deleteJenisHewan(jenisHewanModel.getId_jenis(), pic);

        jenisCall.enqueue(new Callback<ResultOneJenis>() {
            @Override
            public void onResponse(Call<ResultOneJenis> call, Response<ResultOneJenis> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Jenis Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new JenisHewanViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Jenis Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneJenis> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final JenisHewanModel jenisHewanModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, jenisHewanModel);
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
                        deleteJenis(view, jenisHewanModel, pic);
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
        private TextView mJenis, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mParent = itemView.findViewById(R.id.parentJenisHewan);
            mJenis = itemView.findViewById(R.id.textViewJeniss);
            mEditedBy = itemView.findViewById(R.id.textViewPICJenis);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }

}
