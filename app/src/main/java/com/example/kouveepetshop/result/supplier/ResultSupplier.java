package com.example.kouveepetshop.result.supplier;

import com.example.kouveepetshop.model.SupplierModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultSupplier {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<SupplierModel> listSupplier;

    public List<SupplierModel> getListSupplier(){
        return listSupplier;
    }

    public void setListSupplier(List<SupplierModel> listSupplier){
        this.listSupplier = listSupplier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
