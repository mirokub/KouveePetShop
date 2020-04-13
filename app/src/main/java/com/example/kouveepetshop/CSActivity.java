package com.example.kouveepetshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
