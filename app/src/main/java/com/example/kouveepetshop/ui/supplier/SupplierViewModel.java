package com.example.kouveepetshop.ui.supplier;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupplierViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SupplierViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Supplier");
    }

    public LiveData<String> getText() {
        return mText;
    }
}