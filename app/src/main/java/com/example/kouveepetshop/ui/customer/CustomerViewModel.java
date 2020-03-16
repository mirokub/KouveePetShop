package com.example.kouveepetshop.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Customer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}