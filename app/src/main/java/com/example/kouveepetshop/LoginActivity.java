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
            return false;
        }
        if(password == null || password.trim().length() == 0){
            etPassword.setError("Password is required");
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
