package com.example.myapplication.adapter;

import com.example.myapplication.model.Article;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    ArrayList<Article> articles;

    public MainSliderAdapter(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        switch (position) {
            case 0:
                viewHolder.bindImageSlide(getArticles().get(position).getUrlToImage());

                break;
            case 1:
                viewHolder.bindImageSlide(getArticles().get(position).getUrlToImage());
                break;

        }

    }


}
