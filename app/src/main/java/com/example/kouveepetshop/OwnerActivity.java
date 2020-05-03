package com.example.kouveepetshop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanAddFragment;
import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanViewFragment;
import com.example.kouveepetshop.ui.layanan.LayananAddFragment;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;
import com.example.kouveepetshop.ui.pengadaan_menu.PengadaanMenu;
import com.example.kouveepetshop.ui.produk.ProdukAddFragment;
import com.example.kouveepetshop.ui.produk.ProdukViewFragment;
import com.example.kouveepetshop.ui.supplier.SupplierAddFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanAddFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class OwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        final BottomNavigationView navView = findViewById(R.id.nav_view_owner);
        navView.setOnNavigationItemSelectedListener(navListener);

        final FloatingActionButton fab = findViewById(R.id.fab_btn_owner);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navView.getSelectedItemId() == R.id.navigation_produk) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_penjualan_layanan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new LayananAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_jenis_hewan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new JenisHewanAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_ukuran_hewan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new UkuranHewanAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_pengadaan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanMenu()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_supplier){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new SupplierAddFragment()).commit();
                }
                fab.setVisibility(View.INVISIBLE);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FloatingActionButton fab = findViewById(R.id.fab_btn_owner);
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.navigation_produk:
                    selectedFragment = new ProdukViewFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_penjualan_layanan:
                    selectedFragment = new LayananViewFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_jenis_hewan:
                    selectedFragment = new JenisHewanViewFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_ukuran_hewan:
                    selectedFragment = new UkuranHewanViewFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_pengadaan:
                    selectedFragment = new PengadaanMenu();
                    fab.setVisibility(View.INVISIBLE);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_owner, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
