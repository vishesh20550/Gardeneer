package com.example.gardneer;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    private Calendar selectedDate;
    private SharedPreferences sharedPreferences;
    private JSONArray eventsArray;
    private ListView eventListView;
    private ArrayAdapter<String> eventAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDate = Calendar.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eventsArray = getEventsArray();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        setContentView(R.layout.activity_calendar);
        eventListView = findViewById(R.id.event_list);
        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        eventListView.setAdapter(eventAdapter);
        ExtendedFloatingActionButton addEventFab = findViewById(R.id.add_date_fab);
        addEventFab.setOnClickListener(view -> showAddEventDialog());
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            selectedDate.set(year, month, dayOfMonth);
            displayEventsForSelectedDate();
        });
        displayEventsForSelectedDate();
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
                addEventToSharedPreferences(eventTitle);
                eventAdapter.add(eventTitle);
                eventAdapter.notifyDataSetChanged();
                Toast.makeText(CalendarActivity.this, "Event " + eventTitle + " created for " + df.format(selectedDate.getTime()), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private JSONArray getEventsArray() {
        String eventsJsonString = sharedPreferences.getString("eventsArray", "[]");
        try {
            return new JSONArray(eventsJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private void saveEventsArray(JSONArray eventsArray) {
        sharedPreferences.edit().putString("eventsArray", eventsArray.toString()).apply();
    }

    private void addEventToSharedPreferences(String eventTitle) {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("date", getSelectedDateAsString());
            eventObject.put("title", eventTitle);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        eventsArray.put(eventObject);
        saveEventsArray(eventsArray);
    }

    private String getSelectedDateAsString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(selectedDate.getTime());
    }

    private void displayEventsForSelectedDate() {
        String selectedDateString = getSelectedDateAsString();
        List<String> eventTitles = new ArrayList<>();
        for (int i = 0; i < eventsArray.length(); i++) {
            try {
                JSONObject eventObject = eventsArray.getJSONObject(i);
                String date = eventObject.getString("date");
                if (date.equals(selectedDateString)) {
                    String eventTitle = eventObject.getString("title");
                    eventTitles.add(eventTitle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        eventAdapter.clear();

        if (eventTitles.size() > 0) {
            eventAdapter.addAll(eventTitles);
            String eventsString = TextUtils.join(", ", eventTitles);
            Toast.makeText(this, "Events for " + selectedDateString + ": " + eventsString, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No events for " + selectedDateString, Toast.LENGTH_SHORT).show();
        }
    }



//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finishAffinity();
//        finish();
//    }
}