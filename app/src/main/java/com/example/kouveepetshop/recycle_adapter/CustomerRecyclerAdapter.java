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
import com.example.kouveepetshop.model.CustomerModel;
import com.example.kouveepetshop.result.customer.ResultOneCustomer;
import com.example.kouveepetshop.ui.customer.CustomerEditFragment;
import com.example.kouveepetshop.ui.customer.CustomerViewFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<CustomerModel> result;
    private List<CustomerModel> resultFull;

    public CustomerRecyclerAdapter(Context context, List<CustomerModel> result) {
        this.context = context;
        this.result = result;
        resultFull = new ArrayList<>(result);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_customer, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        UserSharedPreferences SP = new UserSharedPreferences(context.getApplicationContext());
        final String pic = SP.getSpId();
        final CustomerModel customerModel = result.get(position);
        holder.mNamaCustomer.setText(customerModel.getNama_customer());
        holder.mAlamat.setText("Alamat : " + customerModel.getAlamat());
        holder.mTglLahir.setText("Tanggal Lahir : " +customerModel.getTgl_lahir());
        holder.mNomorTelp.setText("Nomor Telepon : " +customerModel.getNo_telp());
        holder.mEditedBy.setText("Edited by " + customerModel.getEdited_by() + " at " + customerModel.getUpdated_at());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view, customerModel, pic);
            }
        });
    }

    private void moveToEditFragment(View view, CustomerModel customerModel){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CustomerEditFragment customerEditFragment = new CustomerEditFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("id_customer", customerModel.getId_customer());
        mBundle.putString("nama_customer", customerModel.getNama_customer());
        mBundle.putString("tgl_lahir", customerModel.getTgl_lahir());
        mBundle.putString("nomor_telp", customerModel.getNo_telp());
        customerEditFragment.setArguments(mBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, customerEditFragment).commit();
    }

    private void deleteCustomer(final View view, CustomerModel customerModel, String pic){
        ApiCustomer apiCustomer = ApiClient.getClient().create(ApiCustomer.class);
        Call<ResultOneCustomer> customerCall = apiCustomer.deleteCustomer(customerModel.getId_customer(), pic);

        customerCall.enqueue(new Callback<ResultOneCustomer>() {
            @Override
            public void onResponse(Call<ResultOneCustomer> call, Response<ResultOneCustomer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "Delete Customer Success !", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new CustomerViewFragment()).commit();
                }else{
                    Toast.makeText(context.getApplicationContext(), "Delete Customer Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneCustomer> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(final View view, final CustomerModel customerModel, final String pic){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Choose Your Action");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToEditFragment(view, customerModel);
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
                        deleteCustomer(view, customerModel, pic);
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
        private TextView mNamaCustomer, mAlamat, mTglLahir, mNomorTelp, mEditedBy;
        private LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNamaCustomer = itemView.findViewById(R.id.txtViewNamaCustomer);
            mAlamat = itemView.findViewById(R.id.txtViewAlamat);
            mTglLahir = itemView.findViewById(R.id.txtViewTglLahir);
            mNomorTelp = itemView.findViewById(R.id.txtViewNomorTelp);
            mEditedBy = itemView.findViewById(R.id.txtViewEditedByCustomer);
            mParent = itemView.findViewById(R.id.parentCustomer);
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
            List<CustomerModel> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(resultFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(CustomerModel customer : resultFull){
                    if(customer.getNama_customer().toLowerCase().contains(filterPattern) ||
                        customer.getNama_customer().toLowerCase().contains(filterPattern) ||
                        customer.getAlamat().toLowerCase().contains(filterPattern) ||
                        customer.getNo_telp().toLowerCase().contains(filterPattern) ||
                        customer.getTgl_lahir().contains(constraint) ||
                        customer.getEdited_by().toLowerCase().contains(filterPattern)){
                        filteredList.add(customer);
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
