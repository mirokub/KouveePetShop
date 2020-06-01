package com.example.kouveepetshop.ui.transaksiOwner;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.SplashScreen;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;

public class TransaksiOwnerFragment extends Fragment {

    ImageButton mBtnSupplier, mBtnPengadaan, mProdukMenipis;

    View myView;

    public static TransaksiOwnerFragment newInstance() {
        return new TransaksiOwnerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_transaksi_owner, container, false);

        setAtribut();

        mBtnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierViewFragment()).commit();
            }
        });

        mBtnPengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
            }
        });

        mProdukMenipis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukMenipisViewFragment()).commit();
            }
        });

        return myView;
    }

    private void setAtribut(){
        mBtnSupplier = myView.findViewById(R.id.image_supplier);
        mBtnPengadaan = myView.findViewById(R.id.image_pengadaan);
        mProdukMenipis = myView.findViewById(R.id.image_produk_menipis);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.SearchTxt).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LogOut:
                showDialog();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogout(){
        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        SP.spEditor.clear();
        SP.saveSPBoolean(UserSharedPreferences.SP_ISLOGIN, false);
        SP.spEditor.apply();
        Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        getActivity().finish();
        startActivity(intent);
    }

    private void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure want to logout ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doLogout();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
