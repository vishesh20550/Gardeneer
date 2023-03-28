package com.example.gardneer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

//https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ

public class DetailActivity extends AppCompatActivity {
    PlantBasicDetails selectedPlant;
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

    Activity activity;

//    1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0
//    https://spreadsheets.google.com/feeds/list/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/od6/public/values?alt=json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent previousIntent = getIntent();
        activity = this;
        int id = previousIntent.getIntExtra("id", -1);
        headerData = previousIntent.getStringArrayListExtra("headerData");
        plantData = previousIntent.getStringArrayListExtra("plantData");
        selectedPlant = getParsedPlant(id);
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
        plantNameTV.setText(selectedPlant.getName());
        plantImageTV.setImageResource(selectedPlant.getImage());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void settingOnclicklisteners(){
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
        addToListButton.setOnClickListener(view -> {
            if(plantData != null && headerData != null) {
                try{
                    JSONObject plantDataToSave = new JSONObject();
                    plantDataToSave.put("id", selectedPlant.getId());
                    plantDataToSave.put("name", selectedPlant.getName());
                    plantDataToSave.put("image", selectedPlant.getImage());
                    if (plantData.get(headerData.indexOf("seed")) != null) {
                        plantDataToSave.put("seed", plantData.get(headerData.indexOf("seed")).trim());
                    } else {
                        plantDataToSave.put("seed", "NA");
                    }
                    if (plantData.get(headerData.indexOf("weather_requirement")) != null) {
                        plantDataToSave.put("weather_requirement", plantData.get(headerData.indexOf("weather_requirement")).trim());
                    } else {
                        plantDataToSave.put("weather_requirement", "NA");
                    }
                    if (plantData.get(headerData.indexOf("sprout_to_harvest")) != null) {
                        plantDataToSave.put("sprout_to_harvest", plantData.get(headerData.indexOf("sprout_to_harvest")).trim());
                    } else {
                        plantDataToSave.put("sprout_to_harvest", "NA");
                    }
                    if (plantData.get(headerData.indexOf("season")) != null) {
                        plantDataToSave.put("season", plantData.get(headerData.indexOf("season")).trim());
                    } else {
                        plantDataToSave.put("season", "NA");
                    }
                    if (plantData.get(headerData.indexOf("water")) != null) {
                        plantDataToSave.put("water", plantData.get(headerData.indexOf("water")).trim());
                    } else {
                        plantDataToSave.put("water", "NA");
                    }
                    SharedPreferences savedPlants = getSharedPreferences("savedPlants", MODE_PRIVATE);
                    Map<String, ?> allPlantMap = savedPlants.getAll();
                    //checking if the id already exist or not
                    String id = String.valueOf(selectedPlant.getId());
                    boolean flag1 = false;
                    for (Map.Entry<String, ?> entry : allPlantMap.entrySet()) {
                        String key = entry.getKey();
                        if (key.equals(id)) {
                            flag1 = true;
                        }
                    }
                    if (flag1 == false) {
                        //If not then add it to the SharedPreferences
                        SharedPreferences.Editor myEdit = savedPlants.edit();
                        myEdit.putString(String.valueOf(id), plantDataToSave.toString());
                        myEdit.apply();
                    } else {
                        // else show toast that it was already saved
                        Toast.makeText(activity, "Plant Already Added", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                // Clear top of the intent and go to the HomeActivity
                Toast.makeText(activity,"Cannot add. Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                finish();
            }
            LayoutInflater inflater = LayoutInflater.from(this);
            View popupView = inflater.inflate(R.layout.popup_layout_detailactivity, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
            builder.setView(popupView);
            AlertDialog dialog = builder.create();
            Button add_more = popupView.findViewById(R.id.add_more_Detail_Activity);
            add_more.setOnClickListener(v -> {
                        // Clear top of the intent and go to the HomeActivity
                    Intent intent = new Intent(activity, SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    dialog.dismiss();
                    activity.startActivity(intent);
                    finish();
            });

            Button back_to_menu = popupView.findViewById(R.id.back_to_menu_Detail_Activity);
            back_to_menu.setOnClickListener(v -> {
                    // Clear top of the intent and go to the HomeActivity
                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    dialog.dismiss();
                    activity.startActivity(intent);
                    finish();
            });
            dialog.show();

        });
    }

    public void setList(){
        GoodNeighboursList = new ArrayList<>();
        BadNeighboursList = new ArrayList<>();
//        Map<String, Integer> map = SearchActivity.map;

        if(plantData != null && headerData != null){
            String nameString = plantData.get(headerData.indexOf("good_neighbous")).trim();
            String image_idString = plantData.get(headerData.indexOf("good_neighbous_image_id")).trim();
            if(!nameString.equals("NA")){
                String[] nameStringArray = nameString.substring(1, nameString.length() - 1).split(", ");
                String[] image_idStringArray = image_idString.substring(1, image_idString.length() - 1).split("\\s*,\\s*");
                for (int i =0 ; i < nameStringArray.length; i++){
                    PlantBasicDetails plant;
                    int resourceId = getResources().getIdentifier(image_idStringArray[i], "drawable", getPackageName());
                    plant = new PlantBasicDetails(i, nameStringArray[i], resourceId);
//                    if(map.containsKey(nameStringArray[i])){
//                        plant = new PlantBasicDetails(i, nameStringArray[i], map.get(nameStringArray[i]));
//                    }
//                    else{
//                        plant = new PlantBasicDetails(i, nameStringArray[i], R.drawable.circle_background);
//                    }
                    GoodNeighboursList.add(plant);
                }
            }
            else{
                PlantBasicDetails plant = new PlantBasicDetails(-1, "No known Good Plant", R.drawable.circle_background);
                GoodNeighboursList.add(plant);
            }
            String nameString2 = plantData.get(headerData.indexOf("bad_neighbours")).trim();
            String image_idString2 = plantData.get(headerData.indexOf("bad_neighbours_image_id")).trim();
            if(!nameString2.equals("NA")){
                String[] nameStringArray = nameString2.substring(1, nameString2.length() - 1).split(", ");
                String[] image_idStringArray = image_idString2.substring(1, image_idString2.length() - 1).split("\\s*,\\s*");
                for (int i =0 ; i < nameStringArray.length; i++){
                    PlantBasicDetails plant;
                    int resourceId = getResources().getIdentifier(image_idStringArray[i], "drawable", getPackageName());
                    plant = new PlantBasicDetails(i, nameStringArray[i], resourceId);
//                    if(map.containsKey(nameStringArray[i])){
//                        plant = new PlantBasicDetails(i, nameStringArray[i], map.get(nameStringArray[i]));
//                    }
//                    else{
//                        plant = new PlantBasicDetails(i, nameStringArray[i], R.drawable.circle_background);
//                    }
                    BadNeighboursList.add(plant);
                }
            }
            else{
                PlantBasicDetails plant = new PlantBasicDetails(-1, "No known Bad Plant", R.drawable.circle_background);
                BadNeighboursList.add(plant);
            }
        }
        else {
            PlantBasicDetails plant = new PlantBasicDetails(-1, "No-Data", R.drawable.circle_background);
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

    private PlantBasicDetails getParsedPlant(int parsedID) {
        for (PlantBasicDetails plant : SearchActivity.plantList) {
            if(plant.getId() == parsedID)
                return plant;
        }
        return null;
    }
}