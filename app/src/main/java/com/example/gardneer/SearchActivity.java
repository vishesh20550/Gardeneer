package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<PlantBasicDetails> plantList;
//    public static Map<String, Integer> map;
    Activity activity;
    private String Filtertype = "all";
    ArrayList<PlantBasicDetails> filteredplants;
    private RecyclerView recyclerView;

    private SearchView searchView;
    private TextView backButton;
    private ImageView filterButton;

    public static int[] fruit_and_vegetable = {0, 1, 2,3,4,5,6,7,8};
    public static int[] flower = {9,10,11,12};
    public static int[] herb = {13,14};
    public static int[] houseplant = {15,16};
    public static int[] warm_weather = {0,7,8,9,10,11,13,14,15,16};
    public static int[] cool_weather = {1,2,3,5,6,12};
    public static int[] perennial_weather = {4};
    public static int[] north_Zone = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    public static int[] south_Zone = {0,1,2,4,5,6,7,8,9,10,12,13,14,15};
    public static int[] east_Zone = {0,1,3,4,5,6,7,8,9,10,11,12,13,14,15};
    public static int[] west_Zone = {0,1,4,5,7,9,10,12,13,14,15};
    public static int[] central_Zone = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    boolean season_filter = false;
    boolean zone_filter = false;
    boolean item_filter = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        backButton = findViewById(R.id.backbutton_searchactivity);
        filterButton = findViewById(R.id.FilterSearchActivity);
        onclicklisterners();
        settingFilter();
        setPlantData();
//        try {
//            checkconnection();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void onclicklisterners() {
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, filterButton);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu_searchactivity, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.clear_filter:
                                Filtertype = "all";
                                filteredplants.clear();
                                PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, plantList);
                                recyclerView.setAdapter(adapter);
                                return true;
                            case R.id.cool_season:
                                Filtertype = "season";
                                filterList(cool_weather);
                                season_filter = true;
                                return true;
                            case R.id.warm_Season:
                                Filtertype = "season";
                                filterList(warm_weather);
                                season_filter = true;
                                return true;
                            case R.id.perennial_Season:
                                Filtertype = "season";
                                filterList(perennial_weather);
                                season_filter = true;
                                return true;
                            case R.id.east_zone:
                                Filtertype = "zone";
                                filterList(east_Zone);
                                zone_filter = true;
                                return true;
                            case R.id.west_zone:
                                Filtertype = "zone";
                                filterList(west_Zone);
                                zone_filter = true;
                                return true;
                            case R.id.north_zone:
                                Filtertype = "zone";
                                filterList(north_Zone);
                                zone_filter = true;
                                return true;
                            case R.id.south_zone:
                                Filtertype = "zone";
                                filterList(south_Zone);
                                zone_filter = true;
                                return true;
                            case R.id.central_zone:
                                Filtertype = "zone";
                                filterList(central_Zone);
                                zone_filter = true;
                                return true;
                            case R.id.flower_item:
                                Filtertype = "item";
                                filterList(flower);
                                item_filter = true;
                                return true;
                            case R.id.fruitsandvegitables_item:
                                Filtertype = "item";
                                filterList(fruit_and_vegetable);
                                item_filter = true;
                                return true;
                            case R.id.herbs_item:
                                Filtertype = "item";
                                filterList(herb);
                                item_filter = true;
                                return true;
                            case R.id.houseplant_item:
                                Filtertype = "item";
                                filterList(houseplant);
                                item_filter = true;
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });
    }


    private void settingFilter() {
        searchView = (SearchView)findViewById(R.id.plantListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<PlantBasicDetails> plantListFilter = new ArrayList<>();
                if(Filtertype.equals("all")) {
                    for(PlantBasicDetails plant: plantList) {
                        if (plant.getName().toLowerCase().contains(s.toLowerCase())) {
                            plantListFilter.add(plant);
                        }
                    }
                }
                else{
                    for(PlantBasicDetails plant: filteredplants) {
                        if (plant.getName().toLowerCase().contains(s.toLowerCase())) {
                            plantListFilter.add(plant);
                        }
                    }
                }
                PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity,plantListFilter);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
    }

    private void filterList(int [] array) {
        if(Filtertype.equals("season") && season_filter) {
            filteredplants.clear();
            season_filter = false;
            for(int i=0; i<array.length; i++) {
                filteredplants.add(plantList.get(array[i]));
            }
        }
        else if(Filtertype.equals("zone") && zone_filter) {
            filteredplants.clear();
            zone_filter = false;
            for(int i=0; i<array.length; i++) {
                filteredplants.add(plantList.get(array[i]));
            }
        }
        else if(Filtertype.equals("item") && item_filter) {
            filteredplants.clear();
            item_filter = false;
            for(int i=0; i<array.length; i++) {
                filteredplants.add(plantList.get(array[i]));
            }
        }
        else{
            ArrayList<PlantBasicDetails> temp;
            if(filteredplants.size() == 0){
                temp = plantList;
            }
            else{
                temp = (ArrayList<PlantBasicDetails>) filteredplants.clone();
            }
            filteredplants.clear();
            for(int i=0; i<array.length; i++) {
                for(PlantBasicDetails plant : temp){
                    if(plant.getId() == array[i]){
                        filteredplants.add(plant);
                    }
                }
            }
        }
        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, filteredplants);
        recyclerView.setAdapter(adapter);
    }

    private void setPlantData() {
        plantList = new ArrayList<PlantBasicDetails>();
        PlantBasicDetails tomato = new PlantBasicDetails(0, "Tomato", R.drawable.toamto_foreground);
        plantList.add(tomato);
        PlantBasicDetails onion = new PlantBasicDetails(1, "Onion", R.drawable.onion_foreground);
        plantList.add(onion);
        PlantBasicDetails potato = new PlantBasicDetails(2, "Potato", R.drawable.potato_foreground);
        plantList.add(potato);
        PlantBasicDetails cabbage = new PlantBasicDetails(3, "Cabbage", R.drawable.cabbage_foreground);
        plantList.add(cabbage);
        PlantBasicDetails lemon = new PlantBasicDetails(4, "Lemon", R.drawable.lemon_foreground);
        plantList.add(lemon);
        PlantBasicDetails spinach = new PlantBasicDetails(5, "Spinach", R.drawable.spinach_foreground);
        plantList.add(spinach);
        PlantBasicDetails pea = new PlantBasicDetails(6, "Pea", R.drawable.pea_foreground);
        plantList.add(pea);
        PlantBasicDetails eggplant = new PlantBasicDetails(7, "Eggplant", R.drawable.eggplant_foreground);
        plantList.add(eggplant);
        PlantBasicDetails watermenlon = new PlantBasicDetails(8, "Watermelon", R.drawable.watermelon_foreground);
        plantList.add(watermenlon);
        PlantBasicDetails marigold = new PlantBasicDetails(9, "Marigold", R.drawable.marigold_foreground);
        plantList.add(marigold);
        PlantBasicDetails sunflower = new PlantBasicDetails(10, "Sunflower", R.drawable.sunflower_foreground);
        plantList.add(sunflower);
        PlantBasicDetails chamomile = new PlantBasicDetails(11, "Chamomile", R.drawable.chamomile_foreground);
        plantList.add(chamomile);
        PlantBasicDetails rose = new PlantBasicDetails(12, "Rose", R.drawable.rose_foreground);
        plantList.add(rose);
        PlantBasicDetails aloevera = new PlantBasicDetails(13, "Aloevera", R.drawable.aloevera_foreground);
        plantList.add(aloevera);
        PlantBasicDetails tulsi = new PlantBasicDetails(14, "Tulsi", R.drawable.tulsi_foreground);
        plantList.add(tulsi);
        PlantBasicDetails money_plant = new PlantBasicDetails(15, "Money plant", R.drawable.money_plant_foreground);
        plantList.add(money_plant);
        PlantBasicDetails spider_plants = new PlantBasicDetails(16, "Spider plants", R.drawable.spider_plants_foreground);
        plantList.add(spider_plants);
        setRecyclerView();

//        map = new HashMap<String, Integer>();
//        map.put("Tomato", R.drawable.toamto_foreground);
//        map.put("Onion", R.drawable.onion_foreground);
//        map.put("Potato", R.drawable.potato_foreground);
//        map.put("Cabbage", R.drawable.cabbage_foreground);
//        map.put("Lemon", R.drawable.lemon_foreground);
//        map.put("Spinach", R.drawable.spinach_foreground);
//        map.put("Pea", R.drawable.pea_foreground);
//        map.put("Eggplant", R.drawable.eggplant_foreground);
//        map.put("Watermelon", R.drawable.watermelon_foreground);
//        map.put("Marigold", R.drawable.marigold_foreground);
//        map.put("Sunflower", R.drawable.sunflower_foreground);
//        map.put("Chamomile", R.drawable.chamomile_foreground);
//        map.put("Rose", R.drawable.rose_foreground);
//        map.put("Aloevera", R.drawable.aloevera_foreground);
//        map.put("Tulsi", R.drawable.tulsi_foreground);
//        map.put("Money plant", R.drawable.money_plant_foreground);
//        map.put("Spider plants", R.drawable.spider_plants_foreground);
//        map.put("Carrot", R.drawable.carrot_foreground);
//        map.put("Corn", R.drawable.corn_foreground);
//        map.put("Peanut", R.drawable.peanut_foreground);
//        map.put("Beans", R.drawable.beans_foreground);
//        map.put("Radish", R.drawable.radish_foreground);
//        map.put("Thyme", R.drawable.thyme_foreground);
//        map.put("Beets", R.drawable.beets_foreground);
//        map.put("Strawberry", R.drawable.strawberries_foreground);
//        map.put("Pepper", R.drawable.pepper_foreground);
//        map.put("Grape", R.drawable.grape_foreground);
//        map.put("Garlic", R.drawable.garlic_foreground);
//        map.put("Leek", R.drawable.leek_foreground);
//        map.put("Cauliflower", R.drawable.cauliflower_foreground);
//        map.put("Cucumber", R.drawable.cucumber_foreground);
//        map.put("Pumpkin", R.drawable.pumpkin_foreground);
//        map.put("Melon", R.drawable.melon_foreground);
//        map.put("Fennel", R.drawable.fennel_foreground);
//        map.put("Mint", R.drawable.mint_foreground);
//        map.put("Basil", R.drawable.basil_foreground);
//        map.put("Parsley", R.drawable.parsley_foreground);
//        map.put("Lavender", R.drawable.lavender_foreground);
//        map.put("Peppermint", R.drawable.peppermint_foreground);
//        map.put("Anise", R.drawable.anise_foreground);
//        map.put("Dill", R.drawable.dill_foreground);
//        map.put("Ivy", R.drawable.ivy_foreground);
//        map.put("Pothos", R.drawable.pothos_foreground);
//        map.put("Daffodils", R.drawable.daffodils_foreground);
//        map.put("Fern", R.drawable.fern_foreground);







    }

    private void setRecyclerView(){
        filteredplants = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.plantRecycleVeiw);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        PlantAdapterSearchActivity customAdapter = new PlantAdapterSearchActivity(activity, plantList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void checkconnection() throws JSONException {
        if(plantList ==null || plantList.size() == 0) {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // active internet connection download latest file
                setPlantDataFromSheet2();
            } else {
                // No internet connection check buffer
                SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
                if (savejsonarray.contains("JsonArray"))
                    setPlantDataFromSheet1();
                else {
                    Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, "Failed to Download Initial Files", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity,HomeActivity.class);
                    activity.startActivity(intent);
                }
            }
        }
    }

    private void setPlantDataFromSheet2() {
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
        RequestQueue queue = Volley.newRequestQueue(activity);
        plantList = new ArrayList<PlantBasicDetails>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray values = response.getJSONArray("values");
                    if(values.length() != 0){
                        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit1 = savejsonarray.edit();
                        myEdit1.putString("JsonArray", values.toString());
                        myEdit1.apply();
                        setPlantDataFromSheet1();
                        return;
                    }
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // No network connection check if value exist in SharedPreferences
                SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
                if (savejsonarray.contains("JsonArray")) {
                    try {
                        setPlantDataFromSheet1();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, "Failed to Download Initial Files", Toast.LENGTH_SHORT).show();;
                    Intent intent = new Intent(activity,HomeActivity.class);
                    activity.startActivity(intent);
                }
            }
        });
        queue.add(jsonObjectRequest);
    }


    private void setPlantDataFromSheet1() throws JSONException {
        ArrayList<String> headerData = new ArrayList<>();
        ArrayList<String> plantData = new ArrayList<>();
        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
        String jsonString = null;
        Map<String, ?> saveJson = savejsonarray.getAll();
        for (Map.Entry<String, ?> entry : saveJson.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("JsonArray"))
                jsonString = (String) value;
        }
        JSONArray values = new JSONArray(jsonString);
        if (values.length() != 0) {
            JSONArray jArray = (JSONArray) values.get(0);
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    headerData.add(jArray.getString(i));
                }
            }
            for (int i = 1; i < values.length(); i++) {
                JSONArray jsonArray = (JSONArray) values.get(i);
                int plant_id = jsonArray.getInt(headerData.indexOf("id"));
                String plant_name = jsonArray.getString(headerData.indexOf("plant_name"));
                int plant_image = jsonArray.getInt(headerData.indexOf("image_id"));
                PlantBasicDetails plant = new PlantBasicDetails(plant_id, plant_name, plant_image);
                plantList.add(plant);
            }
            setRecyclerView();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

}