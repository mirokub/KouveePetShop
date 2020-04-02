package com.example.kouveepetshop.result.customer;

import com.example.kouveepetshop.model.CustomerModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultCustomer {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private List<CustomerModel> listCustomer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomerModel> getListCustomer() {
        return listCustomer;
    }

    public void setListCustomer(List<CustomerModel> listCustomer) {
        this.listCustomer = listCustomer;
    }
}
