package com.example.kouveepetshop.ui.pengadaan_menu;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.ui.produk.ProdukFragment;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PengadaanMenu extends Fragment {

    public static PengadaanMenu newInstance() {
        return new PengadaanMenu();
    }

    ImageButton mBtnSupplier, mBtnPengadaan;
    View myView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_pengadaan_menu, container, false);

        setAtribut();

        mBtnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new fragment and transaction
                Fragment newFragment = new SupplierViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragment_container_owner, newFragment);
//                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        return myView;
    }

    private void setAtribut(){
        mBtnSupplier = myView.findViewById(R.id.image_supplier);
        mBtnPengadaan = myView.findViewById(R.id.image_pengadaan);
    }

}
