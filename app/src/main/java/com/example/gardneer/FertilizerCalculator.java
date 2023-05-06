package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FertilizerCalculator extends AppCompatActivity {
    private TextView backButton;
    Context activity;
    Spinner spinner;
    EditText number_of_plants;
    Button calculateButton;
    TextView ureaDetails, fertilizerDetails;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_calculator);
        backButton = findViewById(R.id.backbutton_fertilizeractivity);
        activity = this;
        spinner = findViewById(R.id.spinner);
        number_of_plants = findViewById(R.id.editText);
        calculateButton = findViewById(R.id.button);
        ureaDetails = findViewById(R.id.ureaDetails);
        fertilizerDetails = findViewById(R.id.fertilizerDetails);
        scrollView = findViewById(R.id.scrollViewFertilizer);
        scrollView.setVisibility(View.GONE);


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String potSize = spinner.getSelectedItem().toString();
                String numberS = number_of_plants.getText().toString();
                int pot = 0;
                if(potSize.equals("Small"))pot = 1;
                else if(potSize.equals("Medium"))pot = 2;
                else if(potSize.equals("Large"))pot = 4;

                if (potSize.isEmpty() || numberS.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int number = Integer.parseInt(numberS);
                        if (number < 1) {
                            Toast.makeText(getApplicationContext(), "Enter Valid Number of Plants", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(FertilizerCalculator.this, potSize + " "+ pot+ " "+number, Toast.LENGTH_SHORT).show();

                            String fertilizer_S = "Total fertilizer: " + pot*number*0.5+" Teaspoon ("+ pot*number*12.5 + " gram)"+
                                    "\n\nFertilizer per-Plant: " +pot*0.5+" Teaspoon ("+ pot*12.5 + " gram)"+
                                    "\n\nTime Period: Once every 4-6 weeks during the growing season"+
                                    "\n\nConcentration: up to about 1 tablespoon per gallon (4 liters) of potting soil.\n\n" +
                                    "Fertilizer Content: 10-10-10 parts of nitrogen (N), phosphorus (P), and potassium (K).";

                            String urea_S = "Total Urea: " + pot*number*0.5+" Teaspoon ("+ pot*number*12.5 + " gram)"+
                                    "\n\nUrea per-Plant: " +pot*0.5+" Teaspoon ("+ pot*12.5 + " gram)"+
                                    "\n\nTime Period: Once every 4-6 weeks during the growing season"+
                                    "\n\nConcentration: About 30 to 60 ppm (parts per million) of nitrogen in the potting soil.\n\n" +
                                    "Urea Content: Urea fertilizer with a nitrogen (N) content of 46%." ;

                            fertilizerDetails.setText(fertilizer_S);
                            ureaDetails.setText(urea_S);
                            scrollView.setVisibility(View.VISIBLE);

                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid integer number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
            });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        finish();
    }
}