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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gardneer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RecyclerView addPlantRecyclerView;
    String Location_Provider = LocationManager.GPS_PROVIDER;
    View progressOverlay;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle toggle;
    LocationManager locationManager;
    LocationListener locationListener;
    String[] urls;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    ImageButton btn_hamburger,btn_notification,addPlantButton;
    TextView dateWeatherTV,tempWeatherTV,maxMinTempTV,humidityTV;
    String temp,temp_min,temp_max,humidity;
    ImageView currentWeatherImageView;
    ArrayList<PlantBasicDetails> plantInfos = new ArrayList<>();
    public void initialize(){
        progressOverlay =findViewById(R.id.progress_overlay);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        addPlantRecyclerView = findViewById(R.id.addPlantRecyclerView);
        addPlantButton =findViewById(R.id.addPlantButton);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout ) ;
        humidityTV =findViewById(R.id.humidityTV);
        tempWeatherTV=findViewById(R.id.tempWeatherTV);
        maxMinTempTV=findViewById(R.id.maxMinTempTV);
        dateWeatherTV = findViewById(R.id.dateWeatherTV);
        btn_notification=findViewById(R.id.btn_notification);
        currentWeatherImageView=findViewById(R.id.currentWeatherImageView);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
//        btn_hamburger=findViewById(R.id.btn_hamburger);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        addPlantRecyclerView.setLayoutManager(linearLayoutManager);
        AddPlantCustomAdapter addPlantCustomAdapter= new AddPlantCustomAdapter(this,plantInfos);
        addPlantRecyclerView.setAdapter(addPlantCustomAdapter);
        addPlantRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addPlantButton.setOnClickListener(view -> {
            Intent i = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(i);
        });

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout , toolbar , R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close ) ;
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        drawerLayout.addDrawerListener(toggle) ;
        toggle.syncState() ;
        NavigationView navigationView = findViewById(R.id.nav_view ) ;
        navigationView.setNavigationItemSelectedListener( this ) ;
        bottomNavigationView.setSelectedItemId(R.id.myPlantsMenuItem);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id =item.getItemId();
            if (id == R.id.myPlantsMenuItem) {
                Toast.makeText(HomeActivity.this, "My Plants Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }else if (id == R.id.calendarMenuItems) {
                Toast.makeText(HomeActivity.this, "Calendar Clicked", Toast.LENGTH_SHORT).show();
                Intent goToCalendar = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(goToCalendar);
                return true;
            }else if (id == R.id.communityPostsMenuItem) {
                Toast.makeText(HomeActivity.this, "Community Posts Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }else if (id == R.id.tipsMenuItem) {
                Toast.makeText(HomeActivity.this, "Tips Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
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
    @SuppressLint("SetTextI18n")
    public void DownloadTask(){
        executor.execute(() -> {
            StringBuilder result= new StringBuilder();
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url= new URL(urls[0]);
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data= reader.read();
                while(data!=-1){
                    char current = (char) data;
                    result.append(current);
                    data=reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressOverlay.setAnimation(outAnimation);
                progressOverlay.setVisibility(View.GONE);

            }
            handler.post(() -> {
                try{
                    JSONObject jsonObject= new JSONObject(result.toString());
                    String weatherInfo= jsonObject.getString("weather");
                    JSONArray jsonArray= new JSONArray(weatherInfo);
                    StringBuilder weather= new StringBuilder();
                    StringBuilder temperature=new StringBuilder();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1= jsonArray.getJSONObject(i);
                        String main=jsonObject1.getString("main");
                        String des= jsonObject1.getString("description");
                        if(!main.equals("")&&!des.equals("")){
                            weather.append(main).append(" : ").append(des).append("\r\n");
                        }
                    }
                    String temp_info= jsonObject.getString("main");
                    JSONObject jsonObject2= new JSONObject(temp_info);
                    temp=jsonObject2.getString("temp");
                    temp_min= jsonObject2.getString("temp_min");
                    temp_max= jsonObject2.getString("temp_max");
                    humidity =jsonObject2.getString("humidity");
                    temp=Float.toString(Math.round(Float.parseFloat(temp)-273.15f));
                    temp_max=Float.toString(Math.round(Float.parseFloat(temp_max)-273.15f));
                    temp_min=Float.toString(Math.round(Float.parseFloat(temp_min)-273.15f));
                    runOnUiThread(() -> {
                        if(!temp.equals("")){
                            tempWeatherTV.setText(temp+"° C");
                        }
                        if(!temp_min.equals("") && !temp_max.equals("")){
                            maxMinTempTV.setText(temp_max+"°C/"+temp_min+"°C");
                        }
                        if(!humidity.equals("")){
                            humidityTV.setText("Humidity: "+humidity+"%");
                        }
                        progressOverlay.setAnimation(outAnimation);
                        progressOverlay.setVisibility(View.GONE);
                    });

                }catch (Exception e){
                    Toast.makeText(this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressOverlay.setAnimation(outAnimation);
                    progressOverlay.setVisibility(View.GONE);
                }
            });
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
//       getWeatherAtCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    public void getWeatherAtCurrentLocation(){
        progressOverlay.setAnimation(inAnimation);
        progressOverlay.setVisibility(View.VISIBLE);
        SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.getDefault() );
        Date date = new Date();
        dateWeatherTV.setText(dateFormat.format(date));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude =location.getLongitude();
                Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    progressOverlay.setAnimation(outAnimation);
                    progressOverlay.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getLocality();
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                String encodedCityName = null;
                try {
                    encodedCityName = URLEncoder.encode(cityName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    progressOverlay.setAnimation(outAnimation);
                    progressOverlay.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                Log.i("City",cityName);
                urls=new String[]{"https://api.openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid="+"bfd537f8e51d07594d49085bf19f6bfd"};
                DownloadTask();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
                Toast.makeText(HomeActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
                Toast.makeText(HomeActivity.this, "Provider Disabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
                Toast.makeText(HomeActivity.this, "Provider Enabled", Toast.LENGTH_SHORT).show();
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            locationManager.requestLocationUpdates(Location_Provider,5000,5000,locationListener);
        }
    }
}