package com.example.kouveepetshop.ui.pengadaan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PengadaanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PengadaanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Pengadaan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}