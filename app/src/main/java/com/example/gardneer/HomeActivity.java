package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.gardneer.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView addPlantRecyclerView;
    ImageButton btn_hamburger,btn_notification,addPlantButton;
    ArrayList<PlantInfo> plantInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        for (int i =0 ; i<6;i++){
            PlantInfo plantInfo = new PlantInfo();
            plantInfo.name = "Plant"+i;
            plantInfos.add(plantInfo);
        }
        addPlantRecyclerView = findViewById(R.id.addPlantRecyclerView);
        addPlantButton =findViewById(R.id.addPlantButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        addPlantRecyclerView.setLayoutManager(linearLayoutManager);
        AddPlantCustomAdapter addPlantCustomAdapter= new AddPlantCustomAdapter(this,plantInfos);
        addPlantRecyclerView.setAdapter(addPlantCustomAdapter);
        addPlantRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
            }
            });
    }
}