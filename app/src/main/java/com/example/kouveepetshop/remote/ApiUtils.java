package com.example.kouveepetshop.remote;

import retrofit2.Retrofit;

public class ApiUtils {

    public static final String BASE_URL = "http://tugasbesarkami.com/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
