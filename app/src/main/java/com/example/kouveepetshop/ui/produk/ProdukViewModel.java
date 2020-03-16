package com.example.kouveepetshop.ui.produk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdukViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProdukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kelola Data Produk");
    }

    public LiveData<String> getText() {
        return mText;
    }
}