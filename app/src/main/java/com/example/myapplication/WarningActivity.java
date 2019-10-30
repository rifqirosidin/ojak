package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        getSupportActionBar().hide();
    }

    public void back(View view) {
        Intent intent = new Intent(WarningActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
