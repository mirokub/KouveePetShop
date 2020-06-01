package com.example.kouveepetshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.recycle_adapter.ProdukMenipisRecycleAdapter;
import com.example.kouveepetshop.result.produk.ResultProduk;
import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanAddFragment;
import com.example.kouveepetshop.ui.jenis_hewan.JenisHewanViewFragment;
import com.example.kouveepetshop.ui.layanan.LayananAddFragment;
import com.example.kouveepetshop.ui.layanan.LayananViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.PengadaanViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.ProdukMenipisViewFragment;
import com.example.kouveepetshop.ui.transaksiOwner.TransaksiOwnerFragment;
import com.example.kouveepetshop.ui.produk.ProdukAddFragment;
import com.example.kouveepetshop.ui.produk.ProdukViewFragment;
import com.example.kouveepetshop.ui.supplier.SupplierAddFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanAddFragment;
import com.example.kouveepetshop.ui.ukuran_hewan.UkuranHewanViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OwnerActivity extends AppCompatActivity {

    private List<ProdukModel> produkList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        final BottomNavigationView navView = findViewById(R.id.nav_view_owner);
        navView.setOnNavigationItemSelectedListener(navListener);

        final FloatingActionButton fab = findViewById(R.id.fab_btn_owner);
        getNotification();

        String menuFragment = getIntent().getStringExtra("menuFragment");
        if(menuFragment != null){
            System.out.println(menuFragment);
            if(menuFragment.equals("pengadaan")){
                navView.setSelectedItemId(R.id.navigation_pengadaan);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
            }
            if(menuFragment.equals("produk")){
                navView.setSelectedItemId(R.id.navigation_pengadaan);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukMenipisViewFragment()).commit();
            }
        }

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
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new TransaksiOwnerFragment()).commit();
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
                    selectedFragment = new TransaksiOwnerFragment();
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

    private void getNotification(){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResultProduk> pengadaanCall = apiPengadaan.getNotification();

        pengadaanCall.enqueue(new Callback<ResultProduk>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ResultProduk> call, Response<ResultProduk> response) {
                if(response.isSuccessful()) {
                    produkList = response.body().getListProduk();
                    if(produkList.size() > 0){
                        int notificationID = 0;
                        for (ProdukModel produk : produkList) {
                            pushNotification(produk.getNama_produk(), notificationID);
                            notificationID++;
                        }
                    }
                }else{
                    System.out.println("Pesan : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultProduk> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pushNotification(String namaProduk, int notificationID){
        Intent intent = new Intent(getApplicationContext(), OwnerActivity.class);
        intent.putExtra("menuFragment", "pengadaan");
        PendingIntent pengadaan = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Intent intentProduk = new Intent(getApplicationContext(), OwnerActivity.class);
        intentProduk.putExtra("menuFragment", "produk");
        PendingIntent produk = PendingIntent.getActivity(getApplicationContext(), 2, intentProduk, 0);

        NotificationManager notificationManager=(NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        String CHANNEL_ID = "NOTIFICATION";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,"NOTIFICATION_CHANNEL",NotificationManager.IMPORTANCE_DEFAULT);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        Notification notification = builder
                .setContentTitle("Product Out of Stock")
                .setContentText(namaProduk)
                .setSmallIcon(R.mipmap.ic_logo_kouvee)
                .setContentIntent(produk)
                .addAction(R.drawable.ic_action_supplier, "Pengadaan Produk", pengadaan)
                .addAction(R.drawable.ic_action_supplier, "Lihat Produk", produk)
                .setAutoCancel(true)
                .build();

        notificationManager.createNotificationChannel(notificationChannel);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notificationID, notification);
    }

}
