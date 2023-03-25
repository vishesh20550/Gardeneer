package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;

//https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ

public class DetailActivity extends AppCompatActivity {
    PlantBasicDetails selectedShape;
    TextView backButton;
    TextView plantNameTV;
    ImageView plantImageTV;
    LinearLayout addToListButton;
    TextView GerminationSeasonTV, weatherTV, weeksTV, HarvestSeasonTV;
    TextView IntroDetail;
    TextView PlantingDepthTV, WaterTV, GerminationTV, SproutToHarvestTV;
    TextView plantingGuideTV, feedingTV, HarvestTV;
    RecyclerView GoodNeighbours, BadNeighbours;

    ArrayList<PlantBasicDetails> GoodNeighboursList;
    ArrayList<PlantBasicDetails> BadNeighboursList;

    ArrayList<String> listdata;
    ArrayList<String> plantData;

//    1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0
//    https://spreadsheets.google.com/feeds/list/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/od6/public/values?alt=json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        listdata = previousIntent.getStringArrayListExtra("listdata");
        plantData = previousIntent.getStringArrayListExtra("plantData");
        selectedShape = getParsedShape(parsedStringID);
        plantNameTV = (TextView) findViewById(R.id.plantNameDetailActivity);
        plantImageTV = (ImageView) findViewById(R.id.plantImageDetailActivity);
        GerminationSeasonTV = (TextView) findViewById(R.id.GerminationseasonTVDetailActivity);
        weatherTV = (TextView) findViewById(R.id.weatherTVDetailActivity);
        weeksTV = (TextView) findViewById(R.id.weeksTVDetailActivity);
        HarvestSeasonTV = (TextView) findViewById(R.id.HarvestSeasonTVDetailActivity);
        IntroDetail = (TextView) findViewById(R.id.IntroDetailActivity);
        PlantingDepthTV = (TextView) findViewById(R.id.PlantingDepthDetailActivity);
        WaterTV = (TextView) findViewById(R.id.WaterDetailActivity);
        GerminationTV = (TextView) findViewById(R.id.GerminationDetailActivity);
        SproutToHarvestTV = (TextView) findViewById(R.id.SproutToHarvestDetailActivity);
        plantingGuideTV = (TextView) findViewById(R.id.plantingGuideDetailActivity);
        feedingTV = (TextView) findViewById(R.id.feedingDetailActivity);
        HarvestTV = (TextView) findViewById(R.id.HarvestDetailActivity);

        GoodNeighbours = (RecyclerView) findViewById(R.id.GoodNeighbours);
        BadNeighbours =  (RecyclerView) findViewById(R.id.BadNeighbours);

        backButton = (TextView) findViewById(R.id.backButtonDetailActivity);
        addToListButton =  (LinearLayout) findViewById(R.id.addToListDetailActivity);
        settingtext();
        setList();
        settingOnclicklisteners();
    }

    private void settingtext() {
        plantNameTV.setText(selectedShape.getName());
        plantImageTV.setImageResource(selectedShape.getImage());

        if(plantData != null){
            Log.i(selectedShape.getName(), String.valueOf(plantData));
        }

//        GerminationSeasonTV.setText();
//        weatherTV.setText();
//        weeksTV.setText();
//        HarvestSeasonTV.setText();
//
//        IntroDetail.setText();
//        PlantingDepthTV.setText();
//        WaterTV.setText(); GerminationTV.setText();
//        SproutToHarvestTV.setText();
//
//        plantingGuideTV.setText();
//        feedingTV.setText();
//        HarvestTV..setText();
    }

    public void settingOnclicklisteners(){
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        addToListButton.setOnClickListener(view -> {
            Toast.makeText(this,"Code to go to main screen and add this plant to list",Toast.LENGTH_SHORT).show();
        });
    }

    public void setList(){
        GoodNeighboursList = new ArrayList<>();
        BadNeighboursList = new ArrayList<>();

        for (int i =0 ; i<4;i++){
            PlantBasicDetails plant = new PlantBasicDetails(String.valueOf(i), "plant", R.drawable.circle_background);
            GoodNeighboursList.add(plant);
        }
        for (int i =0 ; i<4;i++){
            PlantBasicDetails plant = new PlantBasicDetails(String.valueOf(i), "plant", R.drawable.circle_background);
            BadNeighboursList.add(plant);
        }

        GoodNeighbours.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        PlantAdapterDetailActivity customAdapter = new PlantAdapterDetailActivity(getApplicationContext(), GoodNeighboursList);
        GoodNeighbours.setAdapter(customAdapter);
        GoodNeighbours.setItemAnimator(new DefaultItemAnimator());


        BadNeighbours.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        PlantAdapterDetailActivity customAdapter1 = new PlantAdapterDetailActivity(getApplicationContext(), BadNeighboursList);
        BadNeighbours.setAdapter(customAdapter1);
        BadNeighbours.setItemAnimator(new DefaultItemAnimator());
    }

    private PlantBasicDetails getParsedShape(String parsedID) {
        for (PlantBasicDetails shape : SearchActivity.shapeList) {
            if(shape.getId().equals(parsedID))
                return shape;
        }
        return null;
    }
}