package com.example.gardneer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ

public class DetailActivity extends AppCompatActivity {
    PlantBasicDetails selectedShape;
    TextView backButton;
    TextView plantNameTV, plantScientificName;
    ImageView plantImageTV;
    LinearLayout addToListButton;
    TextView GerminationSeasonTV, weatherTV, weeksTV, HarvestSeasonTV;
    TextView IntroDetail;
    TextView PlantingDepthTV, WaterTV, GerminationTV, SproutToHarvestTV;
    TextView plantingGuideTV, feedingTV, HarvestTV;
    RecyclerView GoodNeighbours, BadNeighbours;

    ArrayList<PlantBasicDetails> GoodNeighboursList;
    ArrayList<PlantBasicDetails> BadNeighboursList;

    ArrayList<String> headerData;
    ArrayList<String> plantData;

//    1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0
//    https://spreadsheets.google.com/feeds/list/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/od6/public/values?alt=json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        headerData = previousIntent.getStringArrayListExtra("headerData");
        plantData = previousIntent.getStringArrayListExtra("plantData");
        selectedShape = getParsedShape(parsedStringID);
        plantNameTV = (TextView) findViewById(R.id.plantNameDetailActivity);
        plantScientificName = (TextView) findViewById(R.id.plantScientificNameDetailActivity);
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

        if(plantData != null && headerData != null){
            if(plantData.get(headerData.indexOf("scientific_name")) != null){
                plantScientificName.setText(plantData.get(headerData.indexOf("scientific_name")).trim());
            }
            if(plantData.get(headerData.indexOf("seed")) != null){
                GerminationSeasonTV.setText(plantData.get(headerData.indexOf("seed")).trim());
            }
            if(plantData.get(headerData.indexOf("weather_requirement")) != null){
                weatherTV.setText(plantData.get(headerData.indexOf("weather_requirement")).trim());
            }
            if(plantData.get(headerData.indexOf("sprout_to_harvest")) != null){
                weeksTV.setText(plantData.get(headerData.indexOf("sprout_to_harvest")).trim());
            }
            if(plantData.get(headerData.indexOf("season")) != null){
                HarvestSeasonTV.setText(plantData.get(headerData.indexOf("season")).trim());
            }
            if(plantData.get(headerData.indexOf("intro")) != null){
                IntroDetail.setText(plantData.get(headerData.indexOf("intro")).trim());
            }
            if(plantData.get(headerData.indexOf("planting_depth")) != null){
                PlantingDepthTV.setText(plantData.get(headerData.indexOf("planting_depth")).trim());
            }
            if(plantData.get(headerData.indexOf("water")) != null){
                WaterTV.setText(plantData.get(headerData.indexOf("water")).trim());
            }
            if(plantData.get(headerData.indexOf("germination")) != null){
                GerminationTV.setText(plantData.get(headerData.indexOf("germination")).trim());
            }
            if(plantData.get(headerData.indexOf("sprout_to_harvest")) != null){
                SproutToHarvestTV.setText(plantData.get(headerData.indexOf("sprout_to_harvest")).trim());
            }
            if(plantData.get(headerData.indexOf("planting_guide")) != null){
                plantingGuideTV.setText(plantData.get(headerData.indexOf("planting_guide")).trim());
            }
            if(plantData.get(headerData.indexOf("feeding")) != null){
                feedingTV.setText(plantData.get(headerData.indexOf("feeding")).trim());
            }
            if(plantData.get(headerData.indexOf("harvest")) != null){
                HarvestTV.setText(plantData.get(headerData.indexOf("harvest")).trim());
            }
        }
        else{
            Toast.makeText(this, "Opening In Minimalistic look", Toast.LENGTH_SHORT).show();
//            String na =  "No-Data";
//            GerminationSeasonTV.setText(na);
//            weatherTV.setText(na);
//            weeksTV.setText(na);
//            HarvestSeasonTV.setText(na);
//
//            IntroDetail.setText(na);
//            PlantingDepthTV.setText(na);
//            WaterTV.setText(na); GerminationTV.setText(na);
//            SproutToHarvestTV.setText(na);
//
//            plantingGuideTV.setText(na);
//            feedingTV.setText(na);
//            HarvestTV.setText(na);
        }
    }

    public void settingOnclicklisteners(){
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
        addToListButton.setOnClickListener(view -> {
            Toast.makeText(this,"Code to go to main screen and add this plant to list",Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = LayoutInflater.from(this);
            View popupView = inflater.inflate(R.layout.popup_layout_detailactivity, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
            AlertDialog alertDialog= builder.create();
            builder.setView(popupView);

            Button add_more = popupView.findViewById(R.id.add_more_Detail_Activity);
            add_more.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // User clicked Button 1
                }
            });

            Button back_to_menu = popupView.findViewById(R.id.back_to_menu_Detail_Activity);
            back_to_menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // User clicked Button 2
                }
            });

// Show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

        });
    }

    public void setList(){
        GoodNeighboursList = new ArrayList<>();
        BadNeighboursList = new ArrayList<>();

        if(plantData != null && headerData != null){
            String inputString = plantData.get(headerData.indexOf("good_neighbous")).trim();
            if(!inputString.equals("NA")){
                String[] stringArray = inputString.substring(1, inputString.length() - 1).split(", ");
                for (int i =0 ; i < stringArray.length; i++){
                    PlantBasicDetails plant = new PlantBasicDetails(String.valueOf(i), stringArray[i], R.drawable.circle_background);
                    GoodNeighboursList.add(plant);
                }
            }
            else{
                PlantBasicDetails plant = new PlantBasicDetails("-1", "No known Goodd Plant", R.drawable.circle_background);
                GoodNeighboursList.add(plant);
            }
            String inputString2 = plantData.get(headerData.indexOf("bad_neighbours")).trim();
            if(!inputString.equals("NA")){
                String[] stringArray = inputString2.substring(1, inputString2.length() - 1).split(", ");
                for (int i =0 ; i < stringArray.length; i++){
                    PlantBasicDetails plant = new PlantBasicDetails(String.valueOf(i), stringArray[i], R.drawable.circle_background);
                    BadNeighboursList.add(plant);
                }
            }
            else{
                PlantBasicDetails plant = new PlantBasicDetails("-1", "No known Bad Plant", R.drawable.circle_background);
                BadNeighboursList.add(plant);
            }
        }
        else {
            PlantBasicDetails plant = new PlantBasicDetails("-1", "No-Data", R.drawable.circle_background);
            GoodNeighboursList.add(plant);
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
        for (PlantBasicDetails shape : SearchActivity.plantList) {
            if(shape.getId().equals(parsedID))
                return shape;
        }
        return null;
    }
}