package com.example.gardneer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            // Handle date selection here
            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
//            values.put(CalendarContract.Events.DTSTART, startMillis);
//            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, "New Event");
            values.put(CalendarContract.Events.DESCRIPTION, "Description");
//            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Kolkata");
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
        finish();
    }
}