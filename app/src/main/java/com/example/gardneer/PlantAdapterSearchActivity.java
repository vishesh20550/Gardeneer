package com.example.gardneer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.view.animation.AlphaAnimation;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ViewHolder itemHolder = (ViewHolder) holder;

        final PlantBasicDetails plant = (PlantBasicDetails) list.get(position);
        itemHolder.textName.setText(plant.getName());
        itemHolder.imageView.setImageResource(plant.getImage());

        itemHolder.linearLayout.setOnClickListener(view -> {
//            Toast.makeText(activity, "Fetching Details", Toast.LENGTH_SHORT).show();
            getDataFromAPI(plant.getName(), plant);
        });

        itemHolder.addbutton.setOnClickListener(view -> {
            Toast.makeText(activity,"Add code to go the the main screen and add the plant",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context,DetailActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            intent.putExtra("id", plant.getName());
//            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private void getDataFromAPI(String plant_name, PlantBasicDetails plant) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id", plant.getId());
                activity.startActivity(intent);
                Toast.makeText(activity, "Fail to get data\nCheck NetworkConnection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

}



