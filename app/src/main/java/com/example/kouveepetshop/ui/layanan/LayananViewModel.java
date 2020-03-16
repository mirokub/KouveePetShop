package com.example.kouveepetshop.ui.layanan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LayananViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LayananViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Layanan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}