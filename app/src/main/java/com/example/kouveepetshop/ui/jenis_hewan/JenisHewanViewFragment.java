package com.example.kouveepetshop.ui.jenis_hewan;

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

public class JenisHewanViewFragment extends Fragment {

    private JenisHewanViewModel jenisHewanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jenisHewanViewModel =
                ViewModelProviders.of(this).get(JenisHewanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jenis_hewan_view, container, false);
        final TextView textView = root.findViewById(R.id.text_jenis_hewan);
        jenisHewanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}