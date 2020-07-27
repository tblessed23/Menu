package com.example.android.popularmoviespractice.fragments;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popularmoviespractice.R;

public class MostPopularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MostPopularFragment())
                .commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
