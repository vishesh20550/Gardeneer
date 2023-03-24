package com.example.gardneer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlantAdapterSearchActivity extends RecyclerView.Adapter<PlantAdapterSearchActivity.ViewHolder>{
    private Context context;

    private List<PlantSearchActivity> list;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.plantName);
            imageView = view.findViewById(R.id.plantImage);
        }
    }


    public PlantAdapterSearchActivity(Context context, List<PlantSearchActivity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PlantAdapterSearchActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shape_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ViewHolder itemHolder = (ViewHolder) holder;

        final PlantSearchActivity plant = (PlantSearchActivity) list.get(position);
        itemHolder.textName.setText(plant.getName());
        itemHolder.imageView.setImageResource(plant.getImage());

        itemHolder.textName.setOnClickListener(view -> {
            Intent intent = new Intent(context,DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("id", plant.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


