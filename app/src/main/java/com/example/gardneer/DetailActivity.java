package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    PlantSearchActivity selectedShape;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();

    }

    private void getSelectedShape()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedShape = getParsedShape(parsedStringID);
    }

    private PlantSearchActivity getParsedShape(String parsedID)
    {
        for (PlantSearchActivity shape : SearchActivity.shapeList)
        {
            if(shape.getId().equals(parsedID))
                return shape;
        }
        return null;
    }

    private void setValues()
    {
        TextView tv = (TextView) findViewById(R.id.plantName);
        ImageView iv = (ImageView) findViewById(R.id.plantImage);

        tv.setText(selectedShape.getId() + " - " + selectedShape.getName());
        iv.setImageResource(selectedShape.getImage());
    }
}