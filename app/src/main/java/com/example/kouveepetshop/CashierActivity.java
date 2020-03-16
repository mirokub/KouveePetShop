package com.example.kouveepetshop;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CashierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        BottomNavigationView navView = findViewById(R.id.nav_view_cashier);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_produk, R.id.navigation_layanan, R.id.navigation_jenis_hewan, R.id.navigation_ukuran_hewan, R.id.navigation_supplier)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cashier);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        FloatingActionButton fab = findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CashierActivity.this, "You clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
