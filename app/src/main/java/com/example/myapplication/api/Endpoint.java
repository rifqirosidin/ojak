package com.example.myapplication.api;

import com.example.myapplication.model.News;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Endpoint {

    @GET("top-headlines?country=us&apiKey=3782be906527468aa05d01858bdf3ea1")
//    Call<JsonObject> getLatestNews();
//    Call<List<News>> getLatestNews();
    Call<News> getLatestNews();

}
