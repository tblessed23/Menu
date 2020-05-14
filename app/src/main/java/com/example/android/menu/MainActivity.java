package com.example.android.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printToLogs(View view) {
        // Find first menu item TextView and print the text to the logs
        TextView textViewItem1 = (TextView) findViewById(R.id.menu_item_1);
        String MenuItem1 = textViewItem1.getText().toString();
        Log.v("MainActivity", MenuItem1);

        // Find second menu item TextView and print the text to the logs
        TextView textViewItem2 = (TextView) findViewById(R.id.menu_item_2);
        String MenuItem2 = textViewItem1.getText().toString();
        Log.v("MainActivity", MenuItem2);

        // Find third menu item TextView and print the text to the logs
        TextView textViewItem3 = (TextView) findViewById(R.id.menu_item_3);
        String MenuItem3= textViewItem1.getText().toString();
        Log.v("MainActivity", MenuItem3);

    }
}