package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Article;
import com.example.myapplication.model.Source;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    ArrayList<Source> sources;
    List<Article> articles;


    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    public NewsAdapter(Context context, JsonArray jsonArray) {

    }

    public List<Article> getArticles() {
        return articles;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int i) {
        Article article = articles.get(i);
//        Source source = sources.get(i);
        viewHolder.tvTitle.setText(getArticles().get(i).getTitle());
        Picasso.get().load(article.getUrlToImage()).into(viewHolder.imgList);
        viewHolder.tvSource.setText(getArticles().get(i).getSource().getName());

    }

    @Override
    public int getItemCount() {
        return getArticles().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgList;
        TextView tvTitle, tvSource;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgList = itemView.findViewById(R.id.img_news_list);
            tvTitle = itemView.findViewById(R.id.tv_title_news_list);
            tvSource = itemView.findViewById(R.id.tv_source_news);

        }
    }
}
