package com.example.kouveepetshop.ui.layanan;

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

public class LayananFragment extends Fragment {

    private LayananViewModel layananViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        layananViewModel =
                ViewModelProviders.of(this).get(LayananViewModel.class);
        View root = inflater.inflate(R.layout.fragment_layanan, container, false);
        final TextView textView = root.findViewById(R.id.text_layanan);
        layananViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}