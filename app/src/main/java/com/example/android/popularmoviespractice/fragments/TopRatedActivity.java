package com.example.android.popularmoviespractice.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popularmoviespractice.R;

public class TopRatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TopRatedFragment())
                .commit();
    }
}
