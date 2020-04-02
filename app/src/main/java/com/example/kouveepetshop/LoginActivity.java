package com.example.kouveepetshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPegawai;
import com.example.kouveepetshop.model.PegawaiModel;
import com.example.kouveepetshop.result.pegawai.ResultPegawai;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private PegawaiModel pegawaiModel;
    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        etUsername = (EditText) findViewById(R.id.txtUsername);
        etPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

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
            etUsername.setError("Username is required");
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            etPassword.setError("Password is required");
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(String username, String password) {
        ApiPegawai apiPegawai = ApiClient.getClient().create(ApiPegawai.class);
        Call<ResultPegawai> callLogin = apiPegawai.login(username, password);

        callLogin.enqueue(new Callback<ResultPegawai>() {
            @Override
            public void onResponse(Call<ResultPegawai> call, Response<ResultPegawai> response) {
                if(response.isSuccessful()){
                    pegawaiModel = response.body().getPegawaiModel();
                    savePreferences(pegawaiModel);
                    Toast.makeText(getApplicationContext(), "Login Success !", Toast.LENGTH_SHORT).show();
                    moveToActivity();
                }else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "Username Not Found !", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 400){
                    Toast.makeText(getApplicationContext(), "Login Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultPegawai> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePreferences(PegawaiModel pegawaiModel){
        UserSharedPreferences SP = new UserSharedPreferences(getApplicationContext());
        SP.saveSPBoolean(UserSharedPreferences.SP_ISLOGIN, true);
        SP.saveSPString(UserSharedPreferences.SP_ID, pegawaiModel.getId_pegawai());
        SP.saveSPString(UserSharedPreferences.SP_NAMA_PEGAWAI, pegawaiModel.getNama_pegawai());
        SP.saveSPString(UserSharedPreferences.SP_USERNAME, pegawaiModel.getUsername());
        SP.saveSPString(UserSharedPreferences.SP_JABATAN, pegawaiModel.getJabatan());
        SP.spEditor.apply();
    }

    private void moveToActivity(){
        UserSharedPreferences SP = new UserSharedPreferences(getApplicationContext());
        if(SP.getSpJabatan().equals("Owner")){
            Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
            finish();
            startActivity(intent);
        }else if(SP.getSpJabatan().equals("CS")){
            Intent intent = new Intent(LoginActivity.this, CSActivity.class);
            finish();
            startActivity(intent);
        }else if(SP.getSpJabatan().equals("Kasir")){
            Intent intent = new Intent(LoginActivity.this, CashierActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
//        Call call = userService.login(username, password);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                if(response.isSuccessful()){
//                    ResObj resObj = (ResObj) response.body();
//                    if(resObj.getMessage().equals("true")){
//                        //login start main activity
//                        Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
//                        intent.putExtra("username", username);
//                        startActivity(intent);
//                    }else {
//                        Toast.makeText(LoginActivity.this,"Username/Password salah.", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this,"Error!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


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
