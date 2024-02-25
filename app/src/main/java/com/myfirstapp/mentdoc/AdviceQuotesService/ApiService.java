package com.myfirstapp.mentdoc.AdviceQuotesService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String BASE_URL = "https://zenquotes.io/api/";
    private static ZenQuotesApi instance;

    public static ZenQuotesApi getInstance() {
        if (instance == null) {

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ZenQuotesApi.class);
        }
        return instance;
    }
}
