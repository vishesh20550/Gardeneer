package com.example.gardneer;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WeeklyGrowthAcitivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<WeekItem> dataList;
    private String WeeksUrl = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet6?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
    TextView button;
    private TextView plantname;
    private ImageView plantimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
        setContentView(R.layout.activity_weekly_growth_acitivity);

        button = findViewById(R.id.backButtonWeeklyGrowthActivity);
        button.setOnClickListener(view -> onBackPressed());

        Intent intent = getIntent();
        int id = intent.getIntExtra("selected", -1);
        int image = intent.getIntExtra("imageurl", -1);
        String plant_name= intent.getStringExtra("name");

        plantname = findViewById(R.id.WeeklyGrowthTextView);
        plantimage = findViewById(R.id.WeeklyGrowthImageView);


        if(id==-1){
            Toast.makeText(this, "No plant selected", Toast.LENGTH_SHORT).show();
            plantname.setText("No Plant selected");
//            plantimage.setImageResource(id);
        }
        else{
            plantname.setText(plant_name);
            plantimage.setImageResource(image);
        }

        recyclerView = findViewById(R.id.weekly_growth_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, WeeksUrl, null,
                response -> {
                    try {
                        Log.d("response", String.valueOf(response));
                        JSONArray rows = response.getJSONArray("values");
                        JSONArray headerrow = rows.getJSONArray(0);
                        JSONArray plantrow = rows.getJSONArray(id+1);
                        for (int i = 2; i < plantrow.length(); i++) {
                            JSONArray row = rows.getJSONArray(i);
                            String heading = headerrow.getString(i);
                            String details = plantrow.getString(i);
                            dataList.add(new WeekItem(heading, details));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);

        adapter = new WGAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    private class WGAdapter extends RecyclerView.Adapter<WGViewHolder> {

        private List<WeekItem> dataList;

        public WGAdapter(List<WeekItem> dataList) {
            this.dataList = dataList;
        }

        @Override
        public WGViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_box_item, parent, false);
            Log.d("WGVIEHOLDER","oncreate");
            return new WGViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WGViewHolder holder, int position) {
            WeekItem data = dataList.get(position);
            holder.headingTextView.setText(data.getHeading());
            holder.detailsTextView.setText(data.getDetails());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private static class WGViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout headingLayout;
        private TextView headingTextView;
        private TextView detailsTextView;
        private ImageView arrowImageView;
        private ScrollView detailsScrollView;
        private boolean isExpanded = false;

        public WGViewHolder(View itemView) {
            super(itemView);
            headingLayout = itemView.findViewById(R.id.heading_layout);
            headingTextView = itemView.findViewById(R.id.heading_text_view);
            detailsTextView = itemView.findViewById(R.id.details_text_view);
            arrowImageView = itemView.findViewById(R.id.arrow_image_view);
            detailsScrollView = itemView.findViewById(R.id.details_scroll_view);
            headingLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v==headingLayout) {
                if (isExpanded) {
                    detailsScrollView.animate().alpha(0.0f).withEndAction(() -> detailsScrollView.setVisibility(View.GONE)).start();
                } else {
                    detailsScrollView.setVisibility(View.VISIBLE);
                    detailsScrollView.setAlpha(0.0f);
                    detailsScrollView.animate().alpha(1.0f).start();
                }

                isExpanded = !isExpanded;

                arrowImageView.setRotation(isExpanded ? 180f : 0f);
            }
        }
    }
}