package com.example.gardneer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlantAdapterDetailActivity extends RecyclerView.Adapter<PlantAdapterDetailActivity.ViewHolder>{
    private Context context;

    private List<PlantBasicDetails> list;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.plantNamePlantCell);
            imageView = view.findViewById(R.id.plantImagePlantCell);
        }
    }


    public PlantAdapterDetailActivity(Context context, List<PlantBasicDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PlantAdapterDetailActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_cell_detailactivity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ViewHolder itemHolder = (ViewHolder) holder;

        final PlantBasicDetails plant = (PlantBasicDetails) list.get(position);
        itemHolder.textName.setText(plant.getName());
        itemHolder.imageView.setImageResource(plant.getImage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


