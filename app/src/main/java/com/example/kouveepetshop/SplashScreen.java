package com.example.kouveepetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activityIntent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(activityIntent);
                finish();
            }
        },2000);

//        final Intent activityIntent;

//        if(Util.getToken() !=null && Util.getToken == 1){
//            activityIntent = new Intent(this, OwnerActivity.class);
//        }else(Util.getToken() != null && Util.getToken == 2){
//            activityIntent = new Intent(this, CSActivity.class);
//        }else(Util.getToken() != null && Util.getToken == 3){
//            activityIntent = new Intent(this, CashierActivity.class);
//        }else{
//            activityIntent = new Intent(this, LoginActivity.class);
//        }

//        startActivity(activityIntent);
//        finish();
    }
}
