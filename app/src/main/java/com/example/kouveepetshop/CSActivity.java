package com.example.kouveepetshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kouveepetshop.ui.customer.CustomerAddFragment;
import com.example.kouveepetshop.ui.customer.CustomerViewFragment;
import com.example.kouveepetshop.ui.hewan.HewanAddFragment;
import com.example.kouveepetshop.ui.hewan.HewanViewFragment;
import com.example.kouveepetshop.ui.layanan.LayananAddFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CSActivity extends AppCompatActivity {

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);
        final BottomNavigationView navView = findViewById(R.id.nav_view_cs);
        navView.setOnNavigationItemSelectedListener(navListener);
        selectedFragment = new CustomerViewFragment();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_produk, R.id.navigation_layanan, R.id.navigation_peliharaan, R.id.navigation_customer, R.id.navigation_transaksi_cs)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cs);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        final FloatingActionButton fab = findViewById(R.id.fab_btn_cs);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navView.getSelectedItemId() == R.id.navigation_customer) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new CustomerAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_hewan) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_cs, new HewanAddFragment()).commit();
                } else if (navView.getSelectedItemId() == R.id.navigation_transaksi) {

                }
                fab.setVisibility(View.INVISIBLE);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FloatingActionButton fab = findViewById(R.id.fab_btn_cs);

            switch (item.getItemId()){
                case R.id.navigation_customer:
                    selectedFragment = new CustomerViewFragment();
                    break;
                case R.id.navigation_hewan:
                    selectedFragment = new HewanViewFragment();
                    break;
                case R.id.navigation_transaksi:
//                    selectedFragment = new JenisHewanFragment();
                    break;
            }
            fab.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_cs, selectedFragment).commit();

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
        Intent intent = new Intent(CSActivity.this, SplashScreen.class);
        finish();
        startActivity(intent);
    }
}
