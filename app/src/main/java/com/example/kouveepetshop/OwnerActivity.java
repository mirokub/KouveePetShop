package com.example.kouveepetshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanFragment;
import com.example.kouveepetshop.ui.layanan.LayananAddFragment;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;
import com.example.kouveepetshop.ui.pengadaan_menu.PengadaanMenu;
import com.example.kouveepetshop.ui.produk.ProdukFragment;
import com.example.kouveepetshop.ui.supplier.SupplierAddFragment;
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

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_produk, R.id.navigation_layanan, R.id.navigation_jenis_hewan, R.id.navigation_ukuran_hewan, R.id.navigation_pengadaan)
//                .build();
//        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_owner);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        final FloatingActionButton fab = findViewById(R.id.fab_btn_owner);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navView.getSelectedItemId() == R.id.navigation_produk) {

                } else if (navView.getSelectedItemId() == R.id.navigation_layanan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new LayananAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_jenis_hewan) {

                } else if (navView.getSelectedItemId() == R.id.navigation_ukuran_hewan) {

                } else if (navView.getSelectedItemId() == R.id.navigation_pengadaan) {

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
                    selectedFragment = new ProdukFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_layanan:
                    selectedFragment = new LayananViewFragment();
                    fab.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_jenis_hewan:
                    selectedFragment = new JenisHewanFragment();
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
//                case R.id.navigation_supplier:
//                    selectedFragment = new SupplierViewFragment();
//                    fab.setVisibility(View.VISIBLE);
//                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_owner, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.LogOut:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doLogout(){
        UserSharedPreferences SP = new UserSharedPreferences(getApplicationContext());
        SP.spEditor.clear();
        SP.saveSPBoolean(UserSharedPreferences.SP_ISLOGIN, false);
        SP.spEditor.apply();
        Intent intent = new Intent(OwnerActivity.this, SplashScreen.class);
        finish();
        startActivity(intent);
    }


}
