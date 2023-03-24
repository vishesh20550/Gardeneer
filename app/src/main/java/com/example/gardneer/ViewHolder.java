package com.example.gardneer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView textName;
    ImageView imageView;
    public ViewHolder(View view) {
        super(view);
        textName = view.findViewById(R.id.plantName);
        imageView = view.findViewById(R.id.plantImage);
    }
}
