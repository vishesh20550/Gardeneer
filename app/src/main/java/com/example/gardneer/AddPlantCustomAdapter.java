package com.example.gardneer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddPlantCustomAdapter extends RecyclerView.Adapter<AddPlantCustomAdapter.ViewHolder>{
    private final Activity activity;
    private final ArrayList<PlantInfoHomeClass> list;
    private int selectedPlant;

    public AddPlantCustomAdapter(Activity activity,ArrayList<PlantInfoHomeClass> list, int selectedPlant) {
        this.activity = activity;
        this.list=list;
        this.selectedPlant = selectedPlant;
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
        PlantInfoHomeClass plant = (PlantInfoHomeClass) list.get(position);
        holder.pantNameTextView.setText(plant.getName());
        holder.plantPicImageView.setImageResource(plant.getImage());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int lastSelectedPlant = selectedPlant;
                notifyItemChanged(selectedPlant);
                selectedPlant = holder.getAdapterPosition();
//                notifyItemChanged(lastSelectedPlant);
                notifyItemChanged(selectedPlant);
                TextView waterTV = activity.findViewById(R.id.waterTV);
                waterTV.setText(plant.getWater());
                TextView seasonTV = activity.findViewById(R.id.seasonTV);
                seasonTV.setText(plant.getSeed());
                TextView weatherTV = activity.findViewById(R.id.weatherTV);
                weatherTV.setText(plant.getWeather_requirement());
                TextView weeksTV = activity.findViewById(R.id.weeksTV);
                weeksTV.setText(plant.getSprout_to_harvest());
                TextView monthsTV = activity.findViewById(R.id.monthsTV);
                monthsTV.setText(plant.getSeason());
                FrameLayout weeklyGrowthLinearLayout = activity.findViewById(R.id.weeklyGrowthLinearLayout);
                weeklyGrowthLinearLayout.setOnClickListener(view ->{
                    //TODO: Write code for Weekly Growth Analysis (Pass current plant using intent)
                });

            }
        });
        if(position == selectedPlant) {
            holder.itemView.setAlpha(1f);
            holder.itemView.setScaleX(1.2f);
            holder.itemView.setScaleY(1.2f);
        }
        else{
            holder.itemView.setAlpha(0.3f);
            holder.itemView.setScaleX(1f);
            holder.itemView.setScaleY(1f);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView plantPicImageView;
        TextView pantNameTextView;
        LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            plantPicImageView = v.findViewById(R.id.plantPicImageView);
            pantNameTextView = v.findViewById(R.id.pantNameTextView);
            linearLayout = v.findViewById(R.id.LLmyPlantLayout);
        }
    }
}
