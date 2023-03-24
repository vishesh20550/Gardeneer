package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{

    public static ArrayList<Plant> shapeList = new ArrayList<Plant>();

    private RecyclerView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initSearchWidgets();
        setupData();
        setUpList();
    }

    private void initSearchWidgets()
    {
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
                ArrayList<Plant> filteredShapes = new ArrayList<Plant>();

                for(Plant shape: shapeList)
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
                PlantAdapter adapter = new PlantAdapter(getApplicationContext(), filteredShapes);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void setupData() {
        Plant cabbage = new Plant("0", "cabbage", R.drawable.cabbage_foreground);
        shapeList.add(cabbage);

        Plant carrot = new Plant("1","carrot", R.drawable.carrot_foreground);
        shapeList.add(carrot);

        Plant corn = new Plant("2","corn", R.drawable.corn_foreground);
        shapeList.add(corn);

        Plant eggplant = new Plant("3","eggplant", R.drawable.eggplant_foreground);
        shapeList.add(eggplant);

        Plant onion = new Plant("4","onion", R.drawable.onion_foreground);
        shapeList.add(onion);

        Plant pea = new Plant("5", "pea", R.drawable.pea_foreground);
        shapeList.add(pea);

        Plant potato = new Plant("6","potato", R.drawable.potato_foreground);
        shapeList.add(potato);

        Plant tomato = new Plant("7","tomato", R.drawable.toamto_foreground);
        shapeList.add(tomato);
    }

    private void setUpList()
    {
        listView = (RecyclerView) findViewById(R.id.shapesListView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        PlantAdapter customAdapter = new PlantAdapter(getApplicationContext(), shapeList);
//        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(customAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
    }




    private void filterList(String status)
    {
        selectedFilter = status;

        ArrayList<Plant> filteredShapes = new ArrayList<Plant>();

        for(Plant plant: shapeList)
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

        PlantAdapter adapter = new PlantAdapter(getApplicationContext(), filteredShapes);
        listView.setAdapter(adapter);
    }




    public void allFilterTapped(View view)
    {
        selectedFilter = "all";

        PlantAdapter adapter = new PlantAdapter(getApplicationContext(), shapeList);
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