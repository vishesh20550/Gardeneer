package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<PlantBasicDetails> plantList;
    Activity activity;
    private RecyclerView listView;
    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    private TextView backButton;
    private ImageView filterButton;

    public static int[] fruit_and_vegetable = {0, 1, 2,3,4,5,6,7,8};
    public static int[] flower = {9,10,11,12};
    public static int[] herb = {13,14};
    public static int[] houseplant = {15,16};
    public static int[] warm_weather = {0,7,8,9,10,11,13,14,15};
    public static int[] cool_weather = {1,2,3,5,6,12};
    public static int[] perennial_weather = {4};
    public static int[] north_Zone = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    public static int[] south_Zone = {0,1,2,4,5,6,7,8,9,10,12,13,14,15};
    public static int[] east_Zone = {0,1,3,4,5,6,7,8,9,10,11,12,13,14,15};
    public static int[] west_Zone = {0,1,4,5,7,9,10,12,13,14,15};
    public static int[] central_Zone = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        backButton = findViewById(R.id.backbutton_searchactivity);
        filterButton = findViewById(R.id.FilterSearchActivity);
        activity = this;
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
//        downloadFolderToCache("https://drive.google.com/file/d/1HtjAIh2gM51QOuviDjSiqPSBoXmPA-37/view?usp=sharing", "data1");
        onclicklisterners();
        initSearchWidgets();
        setPlantData();
        listView = (RecyclerView) findViewById(R.id.shapesListView);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        PlantAdapterSearchActivity customAdapter = new PlantAdapterSearchActivity(activity, plantList);
        listView.setAdapter(customAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
    }

    private void onclicklisterners() {
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
                                selectedFilter = "all";
                                PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, plantList);
                                listView.setAdapter(adapter);
                                return true;
                            case R.id.cool_season:
                                cool_seasonHandler();
                                return true;
                            case R.id.warm_Season:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.perennial_Season:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.east_zone:
                                // Handle sub-menu item one click
                                return true;
                            case R.id.west_zone:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.north_zone:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.south_zone:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.central_zone:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.flower_item:
                                // Handle sub-menu item one click
                                return true;
                            case R.id.fruitsandvegitables_item:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.herbs_item:
                                // Handle sub-menu item two click
                                return true;
                            case R.id.houseplant_item:
                                // Handle sub-menu item two click
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


    private void initSearchWidgets() {
        searchView = (SearchView)findViewById(R.id.shapeListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<PlantBasicDetails> filteredShapes = new ArrayList<>();
                for(PlantBasicDetails shape: plantList) {
                    if(shape.getName().toLowerCase().contains(s.toLowerCase())) {
                        if(selectedFilter.equals("all")) {
                            filteredShapes.add(shape);
                        }
                        else {
                            if(shape.getName().toLowerCase().contains(selectedFilter)) {
                                filteredShapes.add(shape);
                            }
                        }
                    }
                }
                PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity,filteredShapes);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }

    private void setPlantData() {
        plantList = new ArrayList<PlantBasicDetails>();
        PlantBasicDetails tomato = new PlantBasicDetails("0", "Tomato", R.drawable.toamto_foreground);
        plantList.add(tomato);
        PlantBasicDetails onion = new PlantBasicDetails("1", "Onion", R.drawable.onion_foreground);
        plantList.add(onion);
        PlantBasicDetails potato = new PlantBasicDetails("2", "Potato", R.drawable.potato_foreground);
        plantList.add(potato);
        PlantBasicDetails cabbage = new PlantBasicDetails("4", "Cabbage", R.drawable.cabbage_foreground);
        plantList.add(cabbage);
        PlantBasicDetails lemon = new PlantBasicDetails("5", "Lemon", R.drawable.lemon_foreground);
        plantList.add(lemon);
        PlantBasicDetails spinach = new PlantBasicDetails("6", "Spinach", R.drawable.spinach_foreground);
        plantList.add(spinach);
        PlantBasicDetails pea = new PlantBasicDetails("7", "Pea", R.drawable.pea_foreground);
        plantList.add(pea);
        PlantBasicDetails eggplant = new PlantBasicDetails("8", "Eggplant", R.drawable.eggplant_foreground);
        plantList.add(eggplant);
        PlantBasicDetails marigold = new PlantBasicDetails("9", "Marigold", R.drawable.marigold_foreground);
        plantList.add(marigold);
        PlantBasicDetails sunflower = new PlantBasicDetails("10", "Sunflower", R.drawable.sunflower_foreground);
        plantList.add(sunflower);
        PlantBasicDetails chamomile = new PlantBasicDetails("11", "Chamomile", R.drawable.chamomile_foreground);
        plantList.add(chamomile);
        PlantBasicDetails rose = new PlantBasicDetails("12", "Rose", R.drawable.rose_foreground);
        plantList.add(rose);
        PlantBasicDetails aloevera = new PlantBasicDetails("13", "Aloevera", R.drawable.aloevera_foreground);
        plantList.add(aloevera);
        PlantBasicDetails tulsi = new PlantBasicDetails("14", "Tulsi", R.drawable.tulsi_foreground);
        plantList.add(tulsi);
        PlantBasicDetails money_plant = new PlantBasicDetails("15", "Money plant", R.drawable.money_plant_foreground);
        plantList.add(money_plant);
        PlantBasicDetails spider_plants = new PlantBasicDetails("16", "Spider plants", R.drawable.spider_plants_foreground);
        plantList.add(spider_plants);
    }


    private void filterList(int [] array) {
        for(int i=0; i<array.length; i++ ) {
        ArrayList<PlantBasicDetails> filteredShapes = new ArrayList<PlantBasicDetails>();

//        for(PlantBasicDetails plant: plantList) {
//            if(plant.getName().toLowerCase().contains(status)) {
//                if(currentSearchText == "") {
//                    filteredShapes.add(plant);
//                }
//                else {
//                    if(plant.getName().toLowerCase().contains(currentSearchText.toLowerCase())){
//                        filteredShapes.add(plant);
//                    }
//                }
//            }
        }

        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, filteredShapes);
        listView.setAdapter(adapter);
    }

    private void cool_seasonHandler() {
    }

    public void triangleFilterTapped(View view)
    {
        filterList("triangle");
    }

    public void squareFilterTapped(View view)
    {
        filterList("square");
    }

    public void octagonFilterTapped(View view)
    {
        filterList("octagon");
    }

    public void rectangleFilterTapped(View view)
    {
        filterList("rectangle");
    }

    public void circleFilterTapped(View view)
    {
        filterList("circle");
    }

}