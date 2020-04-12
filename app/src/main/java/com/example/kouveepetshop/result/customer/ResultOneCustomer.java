package com.example.kouveepetshop.result.customer;

import com.example.kouveepetshop.model.CustomerModel;
import com.google.gson.annotations.SerializedName;

public class ResultOneCustomer {

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private CustomerModel customerModel;

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) { this.customerModel = customerModel; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
