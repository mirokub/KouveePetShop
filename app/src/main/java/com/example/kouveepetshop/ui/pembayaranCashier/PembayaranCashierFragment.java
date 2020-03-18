package com.example.kouveepetshop.ui.pembayaranCashier;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.ui.peliharaan.PeliharaanViewModel;

public class PembayaranCashierFragment extends Fragment {

    private PembayaranCashierViewModel pembayaranCashierViewModel;

    public static PembayaranCashierFragment newInstance() {
        return new PembayaranCashierFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pembayaran_cashier, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pembayaranCashierViewModel = ViewModelProviders.of(this).get(PembayaranCashierViewModel.class);
        // TODO: Use the ViewModel
    }

}
