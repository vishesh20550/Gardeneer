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

public class SearchActivity extends AppCompatActivity
{

    public static ArrayList<PlantBasicDetails> shapeList;
    Activity activity;
    private RecyclerView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    private TextView backButton;
    private ImageView filterButton;
//    private ProgressBar loadingPB;

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

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, filterButton);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu_searchactivity, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.cold_season:
                                // Handle sub-menu item one click
                                return true;
                            case R.id.warm_Season:
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
        initSearchWidgets();
        setupData();
        setUpList();
    }


    private void initSearchWidgets() {
        searchView = (SearchView)findViewById(R.id.shapeListSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<PlantBasicDetails> filteredShapes = new ArrayList<PlantBasicDetails>();

                for(PlantBasicDetails shape: shapeList) {
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

    private void setupData() {
        shapeList = new ArrayList<PlantBasicDetails>();
        PlantBasicDetails cabbage = new PlantBasicDetails("0", "Cabbage", R.drawable.cabbage_foreground);
        shapeList.add(cabbage);

        PlantBasicDetails carrot = new PlantBasicDetails("1","Carrot", R.drawable.carrot_foreground);
        shapeList.add(carrot);

        PlantBasicDetails corn = new PlantBasicDetails("2","Corn", R.drawable.corn_foreground);
        shapeList.add(corn);

        PlantBasicDetails eggplant = new PlantBasicDetails("3","Eggplant", R.drawable.eggplant_foreground);
        shapeList.add(eggplant);

        PlantBasicDetails onion = new PlantBasicDetails("4","Onion", R.drawable.onion_foreground);
        shapeList.add(onion);

        PlantBasicDetails pea = new PlantBasicDetails("5", "Pea", R.drawable.pea_foreground);
        shapeList.add(pea);

        PlantBasicDetails potato = new PlantBasicDetails("6","Potato", R.drawable.potato_foreground);
        shapeList.add(potato);

        PlantBasicDetails tomato = new PlantBasicDetails("7","Tomato", R.drawable.toamto_foreground);
        shapeList.add(tomato);

        PlantBasicDetails aloevera = new PlantBasicDetails("7","Aloevera", R.drawable.aloevera_foreground);
        shapeList.add(aloevera);

        PlantBasicDetails sunflower = new PlantBasicDetails("7","Sunflower", R.drawable.sunflower_foreground);
        shapeList.add(sunflower);

        PlantBasicDetails marigold  = new PlantBasicDetails("7","marigold", R.drawable.marigold_foreground);
        shapeList.add(marigold);
    }

    private void setUpList() {
        listView = (RecyclerView) findViewById(R.id.shapesListView);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        PlantAdapterSearchActivity customAdapter = new PlantAdapterSearchActivity(activity, shapeList);
        listView.setAdapter(customAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
    }




    private void filterList(String status) {
        selectedFilter = status;
        ArrayList<PlantBasicDetails> filteredShapes = new ArrayList<PlantBasicDetails>();

        for(PlantBasicDetails plant: shapeList) {
            if(plant.getName().toLowerCase().contains(status)) {
                if(currentSearchText == "") {
                    filteredShapes.add(plant);
                }
                else {
                    if(plant.getName().toLowerCase().contains(currentSearchText.toLowerCase())){
                        filteredShapes.add(plant);
                    }
                }
            }
        }

        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, filteredShapes);
        listView.setAdapter(adapter);
    }




    public void allFilterTapped(View view) {
        selectedFilter = "all";

        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(activity, shapeList);
        listView.setAdapter(adapter);
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