package com.example.kouveepetshop.ui.ukuran_hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UkuranHewanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UkuranHewanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Ukuran Hewan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}