package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TipsActivity extends AppCompatActivity {

    TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        button = findViewById(R.id.backButtonTipsActivity);
        button.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}