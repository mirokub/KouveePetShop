package com.example.kouveepetshop.ui.peliharaan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeliharaanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PeliharaanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Peliharaan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}