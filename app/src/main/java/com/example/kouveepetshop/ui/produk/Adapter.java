package com.example.kouveepetshop.ui.produk;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshop.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> data;

    Adapter(Context context, List<String> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view_produk, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String title = data.get(i);
        viewHolder.namaProduk.setText(title);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaProduk, hargaProduk, stokProduk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProduk = itemView.findViewById(R.id.TVNamaProduk);
            hargaProduk = itemView.findViewById(R.id.TVHargaProduk);
            stokProduk = itemView.findViewById(R.id.TVStokProduk);
        }
    }

}
