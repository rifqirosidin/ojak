package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.api.Endpoint;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.model.Article;
import com.example.myapplication.model.News;
import com.example.myapplication.model.Source;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentTabNews extends Fragment {

    RecyclerView rvNews;
    ArrayList<Article> articles;
    NewsAdapter adapter;
    ProgressBar progressBar;
    private String topNews;
    ImageView imgTopNews;
    TextView tvTitleTopNews;
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
            rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            progressBar.setVisibility(View.VISIBLE);
//            imgTopNews = view.findViewById(R.id.img_top_news);
//            tvTitleTopNews = view.findViewById(R.id.tv_title_top_news);
            displayNews();
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
//                for (int i = 0; i < 3 ; i++) {
//                    Picasso.get().load(articles.get(i).getUrlToImage()).into(imgTopNews);
//                    tvTitleTopNews.setText(articles.get(i).getTitle());
//                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }

    public void listNews(List<Article> list){
        System.out.println("response"+list);
        adapter = new NewsAdapter(getContext(), list);
        rvNews.setAdapter(adapter);
    }


}
