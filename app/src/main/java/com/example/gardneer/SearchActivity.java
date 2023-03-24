package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{

    public static ArrayList<PlantSearchActivity> shapeList = new ArrayList<PlantSearchActivity>();

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
//            Intent intent = new Intent(context,DetailActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("id", plant.getName());
//            context.startActivity(intent);
        });

        filterButton.setOnClickListener(view -> {
            Toast.makeText(this,"adddrop down filter",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context,DetailActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("id", plant.getName());
//            context.startActivity(intent);
        });

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
        PlantSearchActivity cabbage = new PlantSearchActivity("0", "cabbage", R.drawable.cabbage_foreground);
        shapeList.add(cabbage);

        PlantSearchActivity carrot = new PlantSearchActivity("1","carrot", R.drawable.carrot_foreground);
        shapeList.add(carrot);

        PlantSearchActivity corn = new PlantSearchActivity("2","corn", R.drawable.corn_foreground);
        shapeList.add(corn);

        PlantSearchActivity eggplant = new PlantSearchActivity("3","eggplant", R.drawable.eggplant_foreground);
        shapeList.add(eggplant);

        PlantSearchActivity onion = new PlantSearchActivity("4","onion", R.drawable.onion_foreground);
        shapeList.add(onion);

        PlantSearchActivity pea = new PlantSearchActivity("5", "pea", R.drawable.pea_foreground);
        shapeList.add(pea);

        PlantSearchActivity potato = new PlantSearchActivity("6","potato", R.drawable.potato_foreground);
        shapeList.add(potato);

        PlantSearchActivity tomato = new PlantSearchActivity("7","tomato", R.drawable.toamto_foreground);
        shapeList.add(tomato);
    }

    private void setUpList()
    {
        listView = (RecyclerView) findViewById(R.id.shapesListView);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        PlantAdapterSearchActivity customAdapter = new PlantAdapterSearchActivity(getApplicationContext(), shapeList);
//        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(customAdapter);
        listView.setItemAnimator(new DefaultItemAnimator());
    }




    private void filterList(String status)
    {
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