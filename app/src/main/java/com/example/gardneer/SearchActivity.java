package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{

    public static ArrayList<PlantSearchActivity> shapeList;

    private RecyclerView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    private TextView backButton;
    private ImageView filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        backButton = findViewById(R.id.searchactivitybackbutton);
        filterButton = findViewById(R.id.FilterSearchActivity);

        backButton.setOnClickListener(view -> {
            Toast.makeText(this,"Define and call on backpressed",Toast.LENGTH_SHORT).show();

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
                            case R.id.season_filter:
                                season_filter_fun();
                                return true;
                            case R.id.zone_filter:
                                zone_filter_fun();
                                return true;
                            case R.id.item_filter:
                                item_filter_fun();
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


    public void season_filter_fun(){
        PopupMenu subPopupMenu = new PopupMenu(SearchActivity.this, filterButton);
        subPopupMenu.getMenuInflater().inflate(R.menu.season_menu_searchactivity, subPopupMenu.getMenu());

        subPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle sub-menu item clicks here
                switch (item.getItemId()) {
                    case R.id.cold_season:
                        // Handle sub-menu item one click
                        return true;
                    case R.id.warm_Season:
                        // Handle sub-menu item two click
                        return true;
                    default:
                        return false;
                }
            }
        });
        subPopupMenu.show();
    }


    public void zone_filter_fun(){
        PopupMenu subPopupMenu = new PopupMenu(SearchActivity.this, filterButton);
        subPopupMenu.getMenuInflater().inflate(R.menu.zone_menu_searchactivity, subPopupMenu.getMenu());

        subPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle sub-menu item clicks here
                switch (item.getItemId()) {
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
                    default:
                        return false;
                }
            }
        });
        subPopupMenu.show();
    }

    public void item_filter_fun(){
        PopupMenu subPopupMenu = new PopupMenu(SearchActivity.this, filterButton);
        subPopupMenu.getMenuInflater().inflate(R.menu.item_menu_searchactivity, subPopupMenu.getMenu());

        subPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle sub-menu item clicks here
                switch (item.getItemId()) {
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
        subPopupMenu.show();
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
                ArrayList<PlantSearchActivity> filteredShapes = new ArrayList<PlantSearchActivity>();

                for(PlantSearchActivity shape: shapeList)
                {
                    if(shape.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("all"))
                        {
                            filteredShapes.add(shape);
                        }
                        else
                        {
                            if(shape.getName().toLowerCase().contains(selectedFilter))
                            {
                                filteredShapes.add(shape);
                            }
                        }
                    }
                }
                PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(getApplicationContext(), filteredShapes);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void setupData() {
        shapeList = new ArrayList<PlantSearchActivity>();
        PlantSearchActivity cabbage = new PlantSearchActivity("0", "Cabbage", R.drawable.cabbage_foreground);
        shapeList.add(cabbage);

        PlantSearchActivity carrot = new PlantSearchActivity("1","Carrot", R.drawable.carrot_foreground);
        shapeList.add(carrot);

        PlantSearchActivity corn = new PlantSearchActivity("2","Corn", R.drawable.corn_foreground);
        shapeList.add(corn);

        PlantSearchActivity eggplant = new PlantSearchActivity("3","Eggplant", R.drawable.eggplant_foreground);
        shapeList.add(eggplant);

        PlantSearchActivity onion = new PlantSearchActivity("4","Onion", R.drawable.onion_foreground);
        shapeList.add(onion);

        PlantSearchActivity pea = new PlantSearchActivity("5", "Pea", R.drawable.pea_foreground);
        shapeList.add(pea);

        PlantSearchActivity potato = new PlantSearchActivity("6","Potato", R.drawable.potato_foreground);
        shapeList.add(potato);

        PlantSearchActivity tomato = new PlantSearchActivity("7","Tomato", R.drawable.toamto_foreground);
        shapeList.add(tomato);

        PlantSearchActivity aloevera = new PlantSearchActivity("7","Aloevera", R.drawable.aloevera_foreground);
        shapeList.add(aloevera);

        PlantSearchActivity sunflower = new PlantSearchActivity("7","Sunflower", R.drawable.sunflower_foreground);
        shapeList.add(sunflower);

        PlantSearchActivity marigold  = new PlantSearchActivity("7","marigold", R.drawable.marigold_foreground);
        shapeList.add(marigold);
    }

    private void setUpList() {
        listView = (RecyclerView) findViewById(R.id.shapesListView);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        PlantAdapterSearchActivity customAdapter = new PlantAdapterSearchActivity(getApplicationContext(), shapeList);
        listView.setAdapter(customAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
    }




    private void filterList(String status) {
        selectedFilter = status;
        ArrayList<PlantSearchActivity> filteredShapes = new ArrayList<PlantSearchActivity>();

        for(PlantSearchActivity plant: shapeList)
        {
            if(plant.getName().toLowerCase().contains(status))
            {
                if(currentSearchText == "")
                {
                    filteredShapes.add(plant);
                }
                else
                {
                    if(plant.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                    {
                        filteredShapes.add(plant);
                    }
                }
            }
        }

        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(getApplicationContext(), filteredShapes);
        listView.setAdapter(adapter);
    }




    public void allFilterTapped(View view)
    {
        selectedFilter = "all";

        PlantAdapterSearchActivity adapter = new PlantAdapterSearchActivity(getApplicationContext(), shapeList);
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