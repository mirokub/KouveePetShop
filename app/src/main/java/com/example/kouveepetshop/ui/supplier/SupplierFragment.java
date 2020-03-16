package com.example.kouveepetshop.ui.supplier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.kouveepetshop.R;

public class SupplierFragment extends Fragment {

    private SupplierViewModel supplierViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supplierViewModel =
                ViewModelProviders.of(this).get(SupplierViewModel.class);
        View root = inflater.inflate(R.layout.fragment_supplier, container, false);
        final TextView textView = root.findViewById(R.id.text_supplier);
        supplierViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}