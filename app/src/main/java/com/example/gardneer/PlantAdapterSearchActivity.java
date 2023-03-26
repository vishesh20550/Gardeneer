package com.example.gardneer;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlantAdapterSearchActivity extends RecyclerView.Adapter<PlantAdapterSearchActivity.ViewHolder>{
    private Activity activity;
    private List<PlantBasicDetails> list;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        ImageView imageView;
        ImageView addbutton;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.plantNameShapeCell);
            imageView = view.findViewById(R.id.plantImageShapeCell);
            linearLayout = view.findViewById(R.id.nextbButtonShapeCell);
            addbutton = view.findViewById(R.id.addButtonShapeCell);
        }
    }


    public PlantAdapterSearchActivity(Activity activity,List<PlantBasicDetails> list) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlantAdapterSearchActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_cell_searchactivity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final PlantBasicDetails plant = (PlantBasicDetails) list.get(position);
        holder.textName.setText(plant.getName());
        holder.imageView.setImageResource(plant.getImage());

        holder.linearLayout.setOnClickListener(view -> {
            try {
                getDataFromSheet(plant.getName(), plant);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        holder.addbutton.setOnClickListener(view -> {
            // loading
            try {
                getDataFromSheetForSaving(plant.getName(), plant);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getDataFromSheet(String plant_name, PlantBasicDetails plant) throws JSONException {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // active internet connection download latest file
            getDataFromSheet2(plant_name, plant);
        } else {
            // No internet connection check buffer
            SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
            if (savejsonarray.contains("JsonArray"))
                getDataFromSheet1(plant_name, plant);
            else{
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id", plant.getId());
                activity.startActivity(intent);
                Toast.makeText(activity, "Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDataFromSheet2(String plant_name, PlantBasicDetails plant) {
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<String> headerData = new ArrayList<>();
                    ArrayList<String> plantData = new ArrayList<>();
                    int flag = 0;
                    JSONArray values = response.getJSONArray("values");
                    if(values.length() != 0){
                        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit1 = savejsonarray.edit();
                        myEdit1.putString("JsonArray", values.toString());
                        myEdit1.apply();
                        getDataFromSheet1(plant_name, plant);
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
                        getDataFromSheet1(plant_name, plant);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Intent intent = new Intent(activity,DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("id", plant.getId());
                    activity.startActivity(intent);
                    Toast.makeText(activity, "Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void getDataFromSheet1(String plant_name, PlantBasicDetails plant) throws JSONException {
        ArrayList<String> headerData = new ArrayList<>();
        ArrayList<String> plantData = new ArrayList<>();
        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
        int flag = 0;
        String jsonString = null;
        Map<String, ?> saveJson = savejsonarray.getAll();
        for (Map.Entry<String, ?> entry : saveJson.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equals("JsonArray"))
                jsonString = (String) value;
        }
        JSONArray values = new JSONArray(jsonString);
        if(values.length() != 0){
            JSONArray jArray = (JSONArray) values.get(0);
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    headerData.add(jArray.getString(i));
                }
            }
            for(int i = 1; i < values.length(); i++){
                JSONArray jsonArray = (JSONArray) values.get(i);
                if (jsonArray != null && (jsonArray.getString(headerData.indexOf("plant_name"))).equals(plant_name)) {
                    flag = 1;
                    for (int j=0;j<jsonArray.length();j++) {
                        plantData.add(jsonArray.getString(j));
                    }
                    Intent intent = new Intent(activity,DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("id", plant.getId());
                    intent.putExtra("plantData", plantData);
                    intent.putExtra("headerData", headerData);
                    activity.startActivity(intent);
                    break;
                }
            }
            if(flag == 0){
                Toast.makeText(activity, "No hit on Dataset", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDataFromSheetForSaving(String plant_name, PlantBasicDetails plant) throws JSONException {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // active internet connection download latest file
            getDataFromSheetForSaving2(plant_name, plant);
        } else {
            // No internet connection check buffer
            SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
            if (savejsonarray.contains("JsonArray"))
                getDataFromSheetForSaving1(plant_name, plant);
            else
                Toast.makeText(activity, "Cannot add. Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
        }
    }

    // Data does not exist need to fetch the data and perform task
    private void getDataFromSheetForSaving2(String plant_name, PlantBasicDetails plant) {
        String url = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet1?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<String> headerData = new ArrayList<>();
                    ArrayList<String> plantData = new ArrayList<>();
                    int flag = 0;
                    JSONArray values = response.getJSONArray("values");
                    if(values.length() != 0){
                        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit1 = savejsonarray.edit();
                        myEdit1.putString("JsonArray", values.toString());
                        myEdit1.apply();
                        getDataFromSheetForSaving1(plant_name, plant);
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
                if (savejsonarray.contains("JsonArray"))
                    getDataFromSheetForSaving2(plant_name, plant);
                else
                    Toast.makeText(activity, "Cannot add. Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    // Data exist just perform the task
    private void getDataFromSheetForSaving1(String plant_name, PlantBasicDetails plant) throws JSONException {
        ArrayList<String> headerData = new ArrayList<>();
        ArrayList<String> plantData = new ArrayList<>();
        SharedPreferences savejsonarray = activity.getSharedPreferences("savedJsonArray", MODE_PRIVATE);
        int flag = 0;
        String jsonString = null;
                Map<String, ?> saveJson = savejsonarray.getAll();
        for (Map.Entry<String, ?> entry : saveJson.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equals("JsonArray"))
                jsonString = (String) value;
        }
        JSONArray values = new JSONArray(jsonString);
        if(values.length() != 0) {
            JSONArray jArray = (JSONArray) values.get(0);
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    headerData.add(jArray.getString(i));
                }
            }
            for (int i = 1; i < values.length(); i++) {
                JSONArray jsonArray = (JSONArray) values.get(i);
                if (jsonArray != null && (jsonArray.getString(headerData.indexOf("plant_name"))).equals(plant_name)) {
                    flag = 1;
                    for (int j = 0; j < jsonArray.length(); j++) {
                        plantData.add(jsonArray.getString(j));
                    }
                    JSONObject plantDataToSave = new JSONObject();
                    plantDataToSave.put("id", plant.getId());
                    plantDataToSave.put("name", plant.getName());
                    plantDataToSave.put("image", plant.getImage());
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
                    SharedPreferences savedPlants = activity.getSharedPreferences("savedPlants", MODE_PRIVATE);
                    Map<String, ?> allPlantMap = savedPlants.getAll();
                    //checking if the id already exist or not
                    String id = plant.getId();
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
                    // Clear top of the intent and go to the HomeActivity
                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", plant.getName());
                    activity.startActivity(intent);
                    break;
                }
            }
            if (flag == 0) {
                Toast.makeText(activity, "No hit on Dataset", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



