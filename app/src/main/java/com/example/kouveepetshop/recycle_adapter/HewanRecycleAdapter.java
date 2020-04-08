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
import com.example.kouveepetshop.api.ApiHewan;
import com.example.kouveepetshop.model.HewanModel;
import com.example.kouveepetshop.result.hewan.ResultOneHewan;
import com.example.kouveepetshop.ui.hewan.HewanEditFragment;
import com.example.kouveepetshop.ui.hewan.HewanViewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanRecycleAdapter extends RecyclerView.Adapter<HewanRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<HewanModel> result;

    public HewanRecycleAdapter(Context context, List<HewanModel> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_hewan, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final HewanModel hewanModel = result.get(position);
        holder.mNamaHewan.setText(hewanModel.getNama_hewan());
        holder.mTglLahir.setText("Tanggal Lahir : " +hewanModel.getTgl_lahir());
        holder.mJenisHewan.setText("Jenis : " +hewanModel.getJenis());
        holder.mUkuranHewan.setText("Ukuran : " +hewanModel.getUkuran());
        holder.mNamaCustomer.setText("Nama Customer : " +hewanModel.getNama_customer());
        holder.mEditedBy.setText("Edited by " + hewanModel.getEdited_by() + " at " + hewanModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, hewanModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, HewanModel hewanModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        HewanEditFragment hewanEditFragment = new HewanEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_hewan", hewanModel.getId_hewan());
        mBundle.putString("nama_hewan", hewanModel.getNama_hewan());
        mBundle.putString("tgl_lahir", hewanModel.getTgl_lahir());
        mBundle.putString("jenis_hewan", hewanModel.getJenis());
        mBundle.putString("ukuran_hewan", hewanModel.getUkuran());
        mBundle.putString("nama_customer", hewanModel.getNama_customer());
        hewanEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, hewanEditFragment).commit();
    }

    private void deleteLayanan(final View view, HewanModel hewanModel, String pic){
        ApiHewan apiHewan = ApiClient.getClient().create(ApiHewan.class);
        Call<ResultOneHewan> hewanCall = apiHewan.deleteHewan(hewanModel.getId_hewan(), pic);

        hewanCall.enqueue(new Callback<ResultOneHewan>() {
            @Override
            public void onResponse(Call<ResultOneHewan> call, Response<ResultOneHewan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Hewan Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new HewanViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Hewan Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneHewan> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final HewanModel hewanModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, hewanModel);
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
                        deleteLayanan(view, hewanModel, pic);
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
        private TextView mNamaHewan, mTglLahir, mJenisHewan, mUkuranHewan, mNamaCustomer, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamaHewan = itemView.findViewById(R.id.txtViewNamaHewan);
            mTglLahir = itemView.findViewById(R.id.txtViewTglLahirHewan);
            mJenisHewan = itemView.findViewById(R.id.txtViewJenisHewan);
            mUkuranHewan = itemView.findViewById(R.id.txtViewUkuranHewan);
            mNamaCustomer = itemView.findViewById(R.id.txtViewNamaCustomerHewan);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByHewan);
            mParent = itemView.findViewById(R.id.parentHewan);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Oh You Touch Me?", Toast.LENGTH_SHORT).show();
        }
    }

}
