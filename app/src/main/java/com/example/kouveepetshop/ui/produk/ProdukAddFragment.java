package com.example.kouveepetshop.ui.produk;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.result.produk.ResultOneProduk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProdukAddFragment extends Fragment {

    private String pic;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;
    View myView;
    EditText mNamaProduk, mSatuan, mHargaJual, mHargaBeli, mStok, mStokMinimum;
    Button mBtnSaveProduk;
    ImageView mGambar;

    String nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, gambar;
    Bitmap bitmap;
    Uri filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_produk_add, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();

        mBtnSaveProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_produk = mNamaProduk.getText().toString();
                satuan = mSatuan.getText().toString();
                harga_jual = mHargaJual.getText().toString();
                harga_beli = mHargaBeli.getText().toString();
                stok = mStok.getText().toString();
                stok_minimum = mStokMinimum.getText().toString();
                gambar = null;
                if (bitmap != null) {
                    gambar = getRealPathFromUri(getActivity(), filePath);
                }

                if(validate(nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, gambar)){
                    ProdukModel produkModel = new ProdukModel(nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, pic);
                    saveProduk(produkModel, gambar);
                }
            }
        });
        return myView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setAtribut(){
        mNamaProduk = myView.findViewById(R.id.etNamaProduk);
        mSatuan = myView.findViewById(R.id.etSatuan);
        mHargaJual = myView.findViewById(R.id.etHargaJual);
        mHargaBeli = myView.findViewById(R.id.etHargaBeli);
        mStok = myView.findViewById(R.id.etStok);
        mStokMinimum = myView.findViewById(R.id.etStokMinimum);
        mBtnSaveProduk = myView.findViewById(R.id.btnSaveProduk);
        mGambar = myView.findViewById(R.id.produkImage);

        mGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)){
                    chooseFile();
                }
            }
        });
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/jpg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    private boolean checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {permission}, requestCode);
            return false;
        }else{
            return true;
        }
    }

    private boolean validate(String namaProduk, String satuan, String hargaJual, String hargaBeli, String stok, String stok_minimum, String gambar){
        if(namaProduk == null || namaProduk.trim().length() == 0){
            mNamaProduk.setError("Nama Produk is required");
            return false;
        }
        if(satuan == null || satuan.trim().length() == 0) {
            mSatuan.setError("Satuan is required");
            return false;
        }
        if(hargaJual == null || hargaJual.trim().length() == 0){
            mHargaBeli.setError("Harga Jual is required");
            return false;
        }
        if(hargaBeli == null || hargaBeli.trim().length() == 0){
            mHargaJual.setError("Harga Beli is required");
            return false;
        }
        if(stok == null || stok.trim().length() == 0){
            mHargaJual.setError("Stok is required");
            return false;
        }
        if(stok_minimum == null || stok_minimum.trim().length() == 0){
            mHargaBeli.setError("Stok minimum is required");
            return false;
        }
        if(gambar == null){
            Toast.makeText(getActivity(), "Image is required !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveProduk(ProdukModel produkModel, String filePath){
        File file = new File(filePath);
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar", file.getName(), image);

        RequestBody nama_produk = RequestBody.create(MediaType.parse("text/plain"), produkModel.getNama_produk());
        RequestBody satuan = RequestBody.create(MediaType.parse("text/plain"), produkModel.getSatuan());
        RequestBody harga_jual = RequestBody.create(MediaType.parse("text/plain"), produkModel.getHarga_jual());
        RequestBody harga_beli = RequestBody.create(MediaType.parse("text/plain"), produkModel.getHarga_beli());
        RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), produkModel.getStok());
        RequestBody stok_minimum = RequestBody.create(MediaType.parse("text/plain"), produkModel.getStok_minimum());
        RequestBody pic = RequestBody.create(MediaType.parse("text/plain"), produkModel.getPic());

        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultOneProduk> produkCall = apiProduk.createProduk(gambar, nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, pic);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Produk Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Adding Produk Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneProduk> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver contentResolver = getActivity().getContentResolver();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath);
                mGambar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Context context, Uri uri){
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(index);
    }
}
