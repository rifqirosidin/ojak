package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailNewsActivity extends AppCompatActivity {
    TextView tvTitle, tvContent, tvAuthor, tvPublish, tvSource;
    ImageView imageView;
    String title, content, author, publish, urlImage, source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        tvTitle = findViewById(R.id.tv_title_detail);
        tvContent = findViewById(R.id.tv_content_detail);
        tvAuthor = findViewById(R.id.tv_author_detail);
        tvPublish = findViewById(R.id.tv_publish_at);
        imageView = findViewById(R.id.img_detail_news);
        tvSource = findViewById(R.id.tv_source_detail);
        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        content = intent.getStringExtra("CONTENT");
        author = intent.getStringExtra("AUTHOR");
        publish = intent.getStringExtra("PUBLISH");
        urlImage = intent.getStringExtra("IMG");
        source = intent.getStringExtra("SOURCE");

        tvTitle.setText(title);
        tvContent.setText(content);
        tvAuthor.setText(author);
        tvPublish.setText(publish);
        tvSource.setText(source);

        Picasso.get().load(urlImage).into(imageView);
    }
}
