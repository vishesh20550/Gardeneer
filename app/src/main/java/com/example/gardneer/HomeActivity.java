package com.example.gardneer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.gardneer.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RecyclerView addPlantRecyclerView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ImageButton btn_hamburger,btn_notification,addPlantButton;
    boolean mToolBarNavigationListenerIsRegistered=false;
    ArrayList<PlantInfo> plantInfos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        btn_hamburger=findViewById(R.id.btn_hamburger);
        for (int i =0 ; i<6;i++){
            PlantInfo plantInfo = new PlantInfo();
            plantInfo.name = "Plant"+i;
            plantInfos.add(plantInfo);
        }
        addPlantRecyclerView = findViewById(R.id.addPlantRecyclerView);
        addPlantButton =findViewById(R.id.addPlantButton);
        toolbar = findViewById(R.id.toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        addPlantRecyclerView.setLayoutManager(linearLayoutManager);
        AddPlantCustomAdapter addPlantCustomAdapter= new AddPlantCustomAdapter(this,plantInfos);
        addPlantRecyclerView.setAdapter(addPlantCustomAdapter);
        addPlantRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addPlantButton.setOnClickListener(view -> {
            Intent i = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(i);
        });
        drawerLayout = findViewById(R.id.drawer_layout ) ;
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout , toolbar , R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close ) ;
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        drawerLayout.addDrawerListener(toggle) ;
        toggle.syncState() ;
        NavigationView navigationView = findViewById(R.id.nav_view ) ;
        navigationView.setNavigationItemSelectedListener( this ) ;
//        btn_hamburger.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id.drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat.START )) {
            drawer.closeDrawer(GravityCompat.START ) ;
        } else {
            super.onBackPressed() ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu , menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId() ;
        if (id == R.id.nav_settings ) {
            return true;
        }
        return super.onOptionsItemSelected(item) ;
    }
    @SuppressWarnings ( "StatementWithEmptyBody" )
    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId() ;
        if (id == R.id.nav_settings ) {
            // Handle the camera action
        } else if (id == R.id.nav_wishlist ) {
        } else if (id == R.id.nav_logout ) {
        } else if (id == R.id.nav_about_us ) {
        } else if (id == R.id.nav_contact_us) {
        } else if (id == R.id.nav_profile) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout ) ;
        drawer.closeDrawer(GravityCompat.START ) ;
        return true;
    }

}