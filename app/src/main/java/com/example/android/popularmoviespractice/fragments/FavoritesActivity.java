package com.example.android.popularmoviespractice.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popularmoviespractice.R;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FavoritesFragment())
                .commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
