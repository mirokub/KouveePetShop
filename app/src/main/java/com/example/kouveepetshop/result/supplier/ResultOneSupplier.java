package com.example.kouveepetshop.result.supplier;

import com.example.kouveepetshop.model.SupplierModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneSupplier {
    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private SupplierModel supplierModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SupplierModel getSupplierModel() {
        return supplierModel;
    }

    public void setSupplierModel(SupplierModel supplierModel) {
        this.supplierModel = supplierModel;
    }
}
