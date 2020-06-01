package com.example.kouveepetshop.ui.transaksiOwner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouveepetshop.R;
import com.example.kouveepetshop.SplashScreen;
import com.example.kouveepetshop.UserSharedPreferences;
import com.example.kouveepetshop.api.ApiClient;
import com.example.kouveepetshop.api.ApiPengadaan;
import com.example.kouveepetshop.model.PengadaanModel;
import com.example.kouveepetshop.ui.supplier.SupplierViewFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuratPemesananFragment extends Fragment {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    FloatingActionButton mBtnSimpanSurat;
    PDFView pdfview;
    String id, nomor_pengadaan;
    String apiUrl = "tugasbesarkami.com/api/pengadaan/surat/show/";

    View myView;

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_show_surat_pemesanan, container, false);

        setAtribut();
        verifyStoragePermissions(getActivity());
        saveToInternal();

        mBtnSimpanSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });

        return myView;
    }

    private void showDialog(View view){
        AlertDialog confirmDialog = new AlertDialog.Builder(getContext()).create();
        confirmDialog.setTitle("");
        confirmDialog.setMessage("Apakah anda yakin ingin mencetak surat pemesanan ini ?");
        confirmDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadFile();
            }
        });
        confirmDialog.show();
    }

    private void setAtribut(){
        mBtnSimpanSurat = myView.findViewById(R.id.fab_btn_simpan_surat);
        pdfview = myView.findViewById(R.id.pdfview);

        Bundle nBundle = getArguments();
        id = nBundle.getString("id");
        nomor_pengadaan = nBundle.getString("nomor_pengadaan");
    }

    private void downloadFile(){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResponseBody> pengadaanCall = apiPengadaan.printSuratPemesanan(id);

        pengadaanCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                    System.out.println("File download was a success? " + String.valueOf(writtenToDisk));
                    if(writtenToDisk){
                        Toast.makeText(getActivity(), "File Saved Successfully to Downloads Folder !", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_owner, new PengadaanViewFragment()).commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File suratPemesanan = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "surat-pemesanan-" + nomor_pengadaan + ".pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(suratPemesanan);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("File Download: " , fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                System.out.println("IOException : " + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
            return false;
        }
    }

    private void saveToInternal(){
        ApiPengadaan apiPengadaan = ApiClient.getClient().create(ApiPengadaan.class);
        Call<ResponseBody> pengadaanCall = apiPengadaan.printSuratPemesanan(id);

        pengadaanCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    boolean writtenToDisk = writeResponseBodyToInternal(response.body());
                    System.out.println("File download was a success? " + String.valueOf(writtenToDisk));

                    if(writtenToDisk){
                        File suratPemesanan = new File(getActivity().getFilesDir() + "/surat-pemesanan-" + nomor_pengadaan + ".pdf");

                        File file = new File(suratPemesanan.getAbsolutePath());
                        pdfview.fromFile(file)
                                .defaultPage(1)
                                .showMinimap(false)
                                .enableSwipe(true)
                                .load();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
            }
        });
    }

    private boolean writeResponseBodyToInternal(ResponseBody body) {
        try {
            File suratPemesanan = new File(getActivity().getFilesDir(), "surat-pemesanan-" + nomor_pengadaan + ".pdf");
            System.out.println(getActivity().getFilesDir());

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(suratPemesanan);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("File Download: " , fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                System.out.println("IOException : " + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
            return false;
        }
    }
}
