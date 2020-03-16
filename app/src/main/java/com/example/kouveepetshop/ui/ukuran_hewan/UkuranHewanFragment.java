package com.example.kouveepetshop.ui.ukuran_hewan;

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

public class UkuranHewanFragment extends Fragment {

    private UkuranHewanViewModel ukuranHewanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ukuranHewanViewModel =
                ViewModelProviders.of(this).get(UkuranHewanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ukuran_hewan, container, false);
        final TextView textView = root.findViewById(R.id.text_ukuran_hewan);
        ukuranHewanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}