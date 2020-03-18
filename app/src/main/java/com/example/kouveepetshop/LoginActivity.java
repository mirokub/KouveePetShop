package com.example.kouveepetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private final String name = "myShared";
    public static final int mode = Activity.MODE_PRIVATE;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        loadPreferences();
        setForm();
    }

    private void setForm() {
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

        txtUsername.setText(username);
        txtPassword.setText(password);
    }

    private void loadPreferences() {
        sp = getSharedPreferences(name, mode);
        if (sp != null) {
            username = sp.getString("username", "");
        }
    }

    private void savePreferences() {
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", txtUsername.getText().toString());
        editor.apply();
    }

    public void loginBtn(View v) {
//        String USER_TYPE_1 = "owner";
//        String USER_TYPE_2 = "cs";
//        String USER_TYPE_3 = "cashier";
//
//        /**After login success you add logic:**/
//
//        Intent intent = null;
//        if(username.equals(USER_TYPE_1)){
//            intent = new Intent(this, OwnerActivity.class);
//        }else if(username.equals(USER_TYPE_2)){
//            intent = new Intent(this, CSActivity.class);
//        }else if(username.equals(USER_TYPE_3)){
//            intent = new Intent(this, CashierActivity.class);
//        }
//
//        if(intent!=null){
//            startActivity(intent);
//        }

//        savePreferences();

//        if(username.equals("owner")){
//            Intent activityIntent = new Intent(LoginActivity.this, OwnerActivity.class);
//            startActivity(activityIntent);
//            finish();
//        }else if(username.equals("cs")){
//            Intent activityIntent = new Intent(LoginActivity.this, CSActivity.class);
//            startActivity(activityIntent);
//            finish();
//        }else if(username.equals("cashier")){
            Intent activityIntent = new Intent(LoginActivity.this, CashierActivity.class);
            startActivity(activityIntent);
            finish();
//        }else{
//            Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
//        }
    }
}
