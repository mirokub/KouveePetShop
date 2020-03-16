package com.example.kouveepetshop.ui.jenis_hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JenisHewanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JenisHewanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Jenis Hewan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}