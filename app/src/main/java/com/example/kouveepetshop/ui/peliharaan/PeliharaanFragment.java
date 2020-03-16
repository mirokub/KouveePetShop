package com.example.kouveepetshop.ui.peliharaan;

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

public class PeliharaanFragment extends Fragment {

    private PeliharaanViewModel peliharaanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peliharaanViewModel=
                ViewModelProviders.of(this).get(PeliharaanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_peliharaan, container, false);
        final TextView textView = root.findViewById(R.id.text_peliharaan);
        peliharaanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}