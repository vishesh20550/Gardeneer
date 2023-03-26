package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;
import com.anthonyfdev.dropdownview.DropDownView;

public class TipsActivity extends AppCompatActivity {

    TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        button = findViewById(R.id.backButtonTipsActivity);
        button.setOnClickListener(view -> {
            onBackPressed();
        });
//        DropDownView dropDownView = (DropDownView) findViewById(R.idCom.drop_down_view);
//        View collapsedView = LayoutInflater.from(this).inflate(R.layout.header_tips_activity, null, false);
//        View expandedView = LayoutInflater.from(this).inflate(R.layout.footer_tips_activity, null, false);
//
//        dropDownView.setHeaderView(collapsedView);
//        dropDownView.setExpandedView(expandedView);
//
//        collapsedView.setOnClickListener(v -> {
//            // on click the drop down will open or close
//            if (dropDownView.isExpanded()) {
//                dropDownView.collapseDropDown();
//            } else {
//                dropDownView.expandDropDown();
//            }
//        });
    }
}