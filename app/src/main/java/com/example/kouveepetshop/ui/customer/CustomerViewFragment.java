package com.example.kouveepetshop.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerViewFragment extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerViewFragment() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Customer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}