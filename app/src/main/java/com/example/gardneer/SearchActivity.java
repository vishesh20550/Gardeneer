package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity
{

    public static ArrayList<Plant> shapeList = new ArrayList<Plant>();

    private ListView listView;
    private Button sortButton;
    private Button filterButton;
    private LinearLayout filterView1;
    private LinearLayout filterView2;
    private LinearLayout sortView;

    boolean sortHidden = true;
    boolean filterHidden = true;

    private Button circleButton, squareButton, rectangleButton, triangleButton, octagonButton, allButton;
    private Button idAscButton, idDescButton, nameAscButton, nameDescButton;

    private ArrayList<String> selectedFilters = new ArrayList<String>();
    private String currentSearchText = "";
    private SearchView searchView;

    private int white, darkGray, red;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchWidgets();
        initWidgets();
        setupData();
        setUpList();
        setUpOnclickListener();
        hideFilter();
        hideSort();
        initColors();
        lookSelected(idAscButton);
        lookSelected(allButton);
        selectedFilters.add("all");
    }

    private void initColors()
    {
        white = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        red = ContextCompat.getColor(getApplicationContext(), R.color.red);
        darkGray = ContextCompat.getColor(getApplicationContext(), R.color.darkerGray);
    }


    private void unSelectAllSortButtons()
    {
        lookUnSelected(idAscButton);
        lookUnSelected(idDescButton);
        lookUnSelected(nameAscButton);
        lookUnSelected(nameDescButton);
    }

    private void unSelectAllFilterButtons()
    {
        lookUnSelected(allButton);
        lookUnSelected(circleButton);
        lookUnSelected(rectangleButton);
        lookUnSelected(octagonButton);
        lookUnSelected(triangleButton);
        lookUnSelected(squareButton);
    }

    private void lookSelected(Button parsedButton)
    {
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(red);
    }

    private void lookUnSelected(Button parsedButton)
    {
        parsedButton.setTextColor(red);
        parsedButton.setBackgroundColor(darkGray);
    }

    private void initWidgets()
    {
        sortButton = (Button) findViewById(R.id.sortButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        filterView1 = (LinearLayout) findViewById(R.id.filterTabsLayout);
        filterView2 = (LinearLayout) findViewById(R.id.filterTabsLayout2);
        sortView = (LinearLayout) findViewById(R.id.sortTabsLayout2);

        circleButton = (Button) findViewById(R.id.circleFilter);
        squareButton = (Button) findViewById(R.id.squareFilter);
        rectangleButton = (Button) findViewById(R.id.rectangleFilter);
        triangleButton  = (Button) findViewById(R.id.triangleFilter);
        octagonButton  = (Button) findViewById(R.id.octagonFilter);
        allButton  = (Button) findViewById(R.id.allFilter);

        idAscButton  = (Button) findViewById(R.id.idAsc);
        idDescButton  = (Button) findViewById(R.id.idDesc);
        nameAscButton  = (Button) findViewById(R.id.nameAsc);
        nameDescButton  = (Button) findViewById(R.id.nameDesc);
    }

    private void initSearchWidgets()
    {
        searchView = (SearchView) findViewById(R.id.shapeListSearchView);

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
                        if(selectedFilters.contains("all"))
                        {
                            filteredShapes.add(shape);
                        }
                        else
                            {
                                for(String filter: selectedFilters)
                                {
                                    if (shape.getName().toLowerCase().contains(filter))
                                    {
                                        filteredShapes.add(shape);
                                    }
                                }
                            }
                    }
                }
                setAdapter(filteredShapes);

                return false;
            }
        });
    }

    private void setupData()
    {
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
        listView = (ListView) findViewById(R.id.shapesListView);

        setAdapter(shapeList);
    }

    private void setUpOnclickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Plant selectShape = (Plant) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });

    }



    private void filterList(String status)
    {
        if(status != null && !selectedFilters.contains(status))
            selectedFilters.add(status);

        ArrayList<Plant> filteredShapes = new ArrayList<Plant>();

        for(Plant shape: shapeList)
        {
            for(String filter: selectedFilters)
            {
                if(shape.getName().toLowerCase().contains(filter))
                {
                    if(currentSearchText == "")
                    {
                        filteredShapes.add(shape);
                    }
                    else
                        {
                            if(shape.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                            {
                                filteredShapes.add(shape);
                            }
                        }
                }
            }
        }

        setAdapter(filteredShapes);
    }




    public void allFilterTapped(View view)
    {
        selectedFilters.clear();
        selectedFilters.add("all");

        unSelectAllFilterButtons();
        lookSelected(allButton);

        setAdapter(shapeList);
    }

    public void triangleFilterTapped(View view)
    {
        filterList("triangle");
        lookSelected(triangleButton);
        lookUnSelected(allButton);
    }

    public void squareFilterTapped(View view)
    {
        filterList("square");
        lookSelected(squareButton);
        lookUnSelected(allButton);
    }

    public void octagonFilterTapped(View view)
    {
        filterList("octagon");
        lookSelected(octagonButton);
        lookUnSelected(allButton);
    }

    public void rectangleFilterTapped(View view)
    {
        filterList("rectangle");
        lookSelected(rectangleButton);
        lookUnSelected(allButton);
    }

    public void circleFilterTapped(View view)
    {
        filterList("circle");
        lookSelected(circleButton);
        lookUnSelected(allButton);
    }


    public void showFilterTapped(View view)
    {
        if(filterHidden == true)
        {
            filterHidden = false;
            showFilter();
        }
        else
            {
                filterHidden = true;
                hideFilter();
            }
    }

    public void showSortTapped(View view)
    {
        if(sortHidden == true)
        {
            sortHidden = false;
            showSort();
        }
        else
        {
            sortHidden = true;
            hideSort();
        }
    }



    private void hideFilter()
    {
        searchView.setVisibility(View.GONE);
        filterView1.setVisibility(View.GONE);
        filterView2.setVisibility(View.GONE);
        filterButton.setText("FILTER");
    }

    private void showFilter()
    {
        searchView.setVisibility(View.VISIBLE);
        filterView1.setVisibility(View.VISIBLE);
        filterView2.setVisibility(View.VISIBLE);
        filterButton.setText("HIDE");
    }

    private void hideSort()
    {
        sortView.setVisibility(View.GONE);
        sortButton.setText("SORT");
    }

    private void showSort()
    {
        sortView.setVisibility(View.VISIBLE);
        sortButton.setText("HIDE");
    }

    public void idASCTapped(View view)
    {
        Collections.sort(shapeList, Plant.idAscending);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(idAscButton);
    }

    public void idDESCTapped(View view)
    {
        Collections.sort(shapeList, Plant.idAscending);
        Collections.reverse(shapeList);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(idDescButton);
    }

    public void nameASCTapped(View view)
    {
        Collections.sort(shapeList, Plant.nameAscending);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(nameAscButton);
    }

    public void nameDESCTapped(View view)
    {
        Collections.sort(shapeList, Plant.nameAscending);
        Collections.reverse(shapeList);
        checkForFilter();
        unSelectAllSortButtons();
        lookSelected(nameDescButton);
    }

    private void checkForFilter()
    {
        if(selectedFilters.contains("all"))
        {
            if(currentSearchText.equals(""))
            {
                setAdapter(shapeList);
            }
            else
                {
                    ArrayList<Plant> filteredShapes = new ArrayList<Plant>();
                    for(Plant shape: shapeList)
                    {
                        if(shape.getName().toLowerCase().contains(currentSearchText))
                        {
                            filteredShapes.add(shape);
                        }
                    }
                    setAdapter(filteredShapes);
                }
        }
        else
            {
                filterList(null);
            }
    }

    private void setAdapter(ArrayList<Plant> shapeList)
    {
        PlantAdapter adapter = new PlantAdapter(getApplicationContext(), 0, shapeList);
        listView.setAdapter(adapter);
    }
}