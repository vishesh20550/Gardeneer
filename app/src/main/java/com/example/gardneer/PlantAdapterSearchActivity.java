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


public class PlantAdapterSearchActivity extends RecyclerView.Adapter<PlantAdapterSearchActivity.ViewHolder>{
    private Context context;

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


    public PlantAdapterSearchActivity(Context context, List<PlantBasicDetails> list) {
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

        final PlantBasicDetails plant = (PlantBasicDetails) list.get(position);
        itemHolder.textName.setText(plant.getName());
        itemHolder.imageView.setImageResource(plant.getImage());

        itemHolder.linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context,DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("id", plant.getId());
            context.startActivity(intent);
        });

        itemHolder.addbutton.setOnClickListener(view -> {
            Toast.makeText(context,"Add code to go the the main screen and add the plant",Toast.LENGTH_SHORT).show();
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
}


