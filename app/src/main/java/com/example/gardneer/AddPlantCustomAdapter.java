package com.example.gardneer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddPlantCustomAdapter extends RecyclerView.Adapter<AddPlantCustomAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<PlantInfo> plantInfos;

    public AddPlantCustomAdapter(Context context,ArrayList<PlantInfo> plantInfos) {
        this.context = context;
        this.plantInfos=plantInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_plants_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.plantPicImageView.setImageResource(R.drawable.ic_launcher_foreground);
//        holder.pantNameTextView.setText(plantInfos.get(position).name);
    }

    @Override
    public int getItemCount() {
        return plantInfos.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView plantPicImageView;
//        TextView pantNameTextView;

        public ViewHolder(View v) {
            super(v);
            plantPicImageView = v.findViewById(R.id.plantPicImageView);
//            pantNameTextView = v.findViewById(R.id.pantNameTextView);
        }
    }
}
