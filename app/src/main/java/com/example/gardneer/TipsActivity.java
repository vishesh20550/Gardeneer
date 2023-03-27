package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<TipItem> dataList;
    private String TipsUrl = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet2?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
    TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        recyclerView = findViewById(R.id.tips_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, TipsUrl, null,
                response -> {
                    try {
                        JSONArray rows = response.getJSONArray("values");
                        for (int i = 1; i < rows.length(); i++) {
                            JSONArray row = rows.getJSONArray(i);
                            String heading = row.getString(0);
                            String details = row.getString(1);
                            dataList.add(new TipItem(heading, details));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);

        adapter = new MyAdapter(dataList);
        recyclerView.setAdapter(adapter);

    }
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<TipItem> dataList;

        public MyAdapter(List<TipItem> dataList) {
            this.dataList = dataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_box_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            TipItem data = dataList.get(position);
            holder.headingTextView.setText(data.getHeading());
            holder.detailsTextView.setText(data.getDetails());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RelativeLayout headingLayout;
        private TextView headingTextView;
        private TextView detailsTextView;
        private ImageView arrowImageView;
        private ScrollView detailsScrollView;
        private boolean isExpanded = false;

        public MyViewHolder(View itemView) {
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}