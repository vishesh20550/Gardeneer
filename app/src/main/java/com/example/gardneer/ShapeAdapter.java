package com.example.gardneer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ShapeAdapter extends ArrayAdapter<PlantBasicDetails>
{

    public ShapeAdapter(Context context, int resource, List<PlantBasicDetails> shapeList)
    {
        super(context,resource,shapeList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PlantBasicDetails shape = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.plant_cell_detailactivity, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.plantNamePlantCell);
        ImageView iv = (ImageView) convertView.findViewById(R.id.plantImagePlantCell);

        tv.setText(shape.getId() + " - " + shape.getName());
        iv.setImageResource(shape.getImage());


        return convertView;
    }
}
