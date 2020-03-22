package com.example.kouveepetshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kouveepetshop.model.ResObj;
import com.example.kouveepetshop.remote.ApiUtils;
import com.example.kouveepetshop.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        etUsername = (EditText) findViewById(R.id.txtUsername);
        etPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLogin(username, password);
                }
            }
        });
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username, final String password){
        Call call = userService.login(username, password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ResObj resObj = (ResObj) response.body();
                    if(resObj.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"Username/Password salah.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
//    private SharedPreferences sp;
//    private final String name = "myShared";
//    public static final int mode = Activity.MODE_PRIVATE;
//
//    EditText txtUsername, txtPassword;
////    private String username = "";
////    private String password = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_form);
//
//        txtUsername = (EditText) findViewById(R.id.txtUsername);
//        txtPassword = (EditText) findViewById(R.id.txtPassword);
//
////        setForm();
////        loadPreferences();
//    }
//
////    private void setForm() {
////        txtUsername.setText(username);
////        txtPassword.setText(password);
////    }
//
////    private void loadPreferences() {
////        sp = getSharedPreferences(name, mode);
////        if (sp != null) {
////            username = sp.getString("username", "");
////        }
////    }
////
////    private void savePreferences() {
////        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
////
////        SharedPreferences.Editor editor = sp.edit();
////        editor.putString("username", txtUsername.getText().toString());
////        editor.apply();
////    }
//
//    public void loginBtn(View v) {
//
//        String username = txtUsername.getText().toString();
//        String password = txtPassword.getText().toString();
//        String type = "login";
//
//        BackgroundLogin backgroundLogin = new BackgroundLogin(this);
//        backgroundLogin.execute(type, username, password);
//
////        String USER_TYPE_1 = "owner";
////        String USER_TYPE_2 = "cs";
////        String USER_TYPE_3 = "cashier";
////
////        /**After login success you add logic:**/
////
////        Intent intent = null;
////        if(username.equals(USER_TYPE_1)){
////            intent = new Intent(this, OwnerActivity.class);
////        }else if(username.equals(USER_TYPE_2)){
////            intent = new Intent(this, CSActivity.class);
////        }else if(username.equals(USER_TYPE_3)){
////            intent = new Intent(this, CashierActivity.class);
////        }
////
////        if(intent!=null){
////            startActivity(intent);
////        }
////
////        savePreferences();
//
////        if(username.equals("owner")){
////            Intent activityIntent = new Intent(LoginActivity.this, OwnerActivity.class);
////            startActivity(activityIntent);
////            finish();
////        }else if(username.equals("cs")){
////            Intent activityIntent = new Intent(LoginActivity.this, CSActivity.class);
////            startActivity(activityIntent);
////            finish();
////        }else if(username.equals("cashier")){
////            Intent activityIntent = new Intent(LoginActivity.this, CashierActivity.class);
////            startActivity(activityIntent);
////            finish();
////        }else{
////            Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
////        }
//    }
//}
