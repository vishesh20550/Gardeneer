package com.example.gardneer;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.CalendarContract;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    private Calendar selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDate = Calendar.getInstance();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        setContentView(R.layout.activity_calendar);
        ExtendedFloatingActionButton addEventFab = findViewById(R.id.add_date_fab);
        addEventFab.setOnClickListener(view -> showAddEventDialog());
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
        });
    }
    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Event");
        final EditText editText = new EditText(this);
        editText.setHint("Event Title");
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String eventTitle = editText.getText().toString();
                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.TITLE, eventTitle);
                values.put(CalendarContract.Events.DESCRIPTION, "Description");
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Kolkata");
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Toast.makeText(CalendarActivity.this, "Event " + eventTitle + " created for " + df.format(selectedDate.getTime()), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finishAffinity();
//        finish();
//    }
}