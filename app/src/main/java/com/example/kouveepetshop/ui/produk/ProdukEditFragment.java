package com.example.kouveepetshop.ui.produk;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiProduk;
import com.example.kouveepetshop.model.ProdukModel;
import com.example.kouveepetshop.recycle_adapter.ProdukRecycleAdapter;
import com.example.kouveepetshop.result.produk.ResultOneProduk;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProdukEditFragment extends Fragment {

    private String id_produk, pic;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;
    View myView;
    EditText mNamaProduk, mSatuan, mHargaJual, mHargaBeli, mStok, mStokMinimum;
    ImageView mGambar;
    String nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, gambar;
    Button mBtnUpdateProduk;
    Bitmap bitmap;
    Uri filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_produk_edit, container, false);

        UserSharedPreferences SP = new UserSharedPreferences(getActivity());
        pic = SP.getSpId();

        setAtribut();
        setText();

        mBtnUpdateProduk.setOnClickListener(new View.OnClickListener() {
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
                }else{
                    gambar = null;
                }

                if(validate(nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum)){
                    ProdukModel produkModel = new ProdukModel(nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, pic);
                    if(gambar == null){
                        updateProdukWithoutImage(id_produk, produkModel);
                    }else{
                        updateProdukWithImage(id_produk, produkModel, gambar);
                    }
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    private void setAtribut(){
        mNamaProduk = myView.findViewById(R.id.etNamaProdukEdit);
        mSatuan = myView.findViewById(R.id.etSatuanEdit);
        mHargaJual = myView.findViewById(R.id.etHargaJualEdit);
        mHargaBeli = myView.findViewById(R.id.etHargaBeliEdit);
        mStok = myView.findViewById(R.id.etStokEdit);
        mStokMinimum = myView.findViewById(R.id.etStokMinimumEdit);
        mBtnUpdateProduk = myView.findViewById(R.id.btnUpdateProduk);
        mGambar = myView.findViewById(R.id.produkImageEdit);

        mGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)){
                    chooseFile();
                }
            }
        });
    }

    public void setText(){
        Bundle nBundle = getArguments();
        id_produk = nBundle.getString("id_produk");
        mNamaProduk.setText(nBundle.getString("nama_produk"));
        mSatuan.setText(nBundle.getString("satuan"));
        mHargaJual.setText(nBundle.getString("harga_jual"));
        mHargaBeli.setText(nBundle.getString("harga_beli"));
        mStok.setText(nBundle.getString("stok"));
        mStokMinimum.setText(nBundle.getString("stok_minimum"));

        String temp = nBundle.getString("gambar");

        Picasso.with(myView.getContext())
                .load("https://tugasbesarkami.com/api/produk/picture/" + temp)
                .placeholder(R.mipmap.ic_no_image)
                .into(mGambar);
    }

    private boolean validate(String namaProduk, String satuan, String hargaJual, String hargaBeli, String stok, String stok_minimum){
        if(namaProduk == null || namaProduk.trim().length() == 0){
            mNamaProduk.setError("Nama Produk is required");
            return false;
        }
        if(satuan == null || satuan.trim().length() == 0){
            mSatuan.setError("Satuan is required");
            return false;
        }
        if(hargaJual == null || hargaJual.trim().length() == 0){
            mHargaJual.setError("Harga Jual is required");
            return false;
        }
        if(hargaBeli == null || hargaBeli.trim().length() == 0){
            mHargaBeli.setError("Harga Beli is required");
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
        return true;
    }

    private void updateProdukWithImage(String id_produk, ProdukModel produkModel, String filePath){
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
        Call<ResultOneProduk> produkCall = apiProduk.updateProdukWithImage(id_produk, gambar, nama_produk, satuan, harga_jual, harga_beli, stok, stok_minimum, pic);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Produk Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Produk Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultOneProduk> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection Problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProdukWithoutImage(String id_produk, ProdukModel produkModel){
        ApiProduk apiProduk = ApiClient.getClient().create(ApiProduk.class);
        Call<ResultOneProduk> produkCall = apiProduk.updateProdukWithoutImage(id_produk, produkModel);

        produkCall.enqueue(new Callback<ResultOneProduk>() {
            @Override
            public void onResponse(Call<ResultOneProduk> call, Response<ResultOneProduk> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Update Produk Success !", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new ProdukViewFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Update Produk Failed !", Toast.LENGTH_SHORT).show();
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
