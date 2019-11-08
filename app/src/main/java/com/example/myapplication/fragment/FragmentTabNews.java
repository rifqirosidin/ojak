package com.example.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.api.Endpoint;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.model.Article;
import com.example.myapplication.model.News;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;


public class FragmentTabNews extends Fragment {

    RecyclerView rvNews;
    ArrayList<Article> articles;
    NewsAdapter adapter;
    ProgressBar progressBar;




    public FragmentTabNews() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_news, container, false);
            rvNews = view.findViewById(R.id.rv_news_list);
            progressBar = view.findViewById(R.id.progressBar);
            rvNews.setHasFixedSize(true);
            rvNews.setLayoutManager(new GridLayoutManager(getContext(), 2));
            progressBar.setVisibility(View.VISIBLE);

            try {
                displayNews();
            } catch (Exception e){
                Toast.makeText(getContext(), "Cek Koneksi Internet", Toast.LENGTH_LONG).show();
            }

        return view;
    }


    public void displayNews(){
        Endpoint endpoint = RetrofitClient.getData().create(Endpoint.class);

        Call<News> call = endpoint.getLatestNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                   articles = new ArrayList<>(response.body().getArticles());

                   adapter = new NewsAdapter(getContext(), articles);
                   rvNews.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(getContext(), "Error Koneksi", Toast.LENGTH_LONG).show();
            }
        });

    }

}




