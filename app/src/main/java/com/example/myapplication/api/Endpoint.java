package com.example.myapplication.api;

import com.example.myapplication.model.News;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Endpoint {

    @GET("top-headlines?country=id&category=health&apiKey=68a59c0b881b42eb851e1fc3fcb60df8")
    Call<News> getLatestNews();

}
