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
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Map;


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
        SharedPreferences savedPlants = getSharedPreferences("savedPlants", MODE_PRIVATE);
        Map<String, ?> allPlantMap = savedPlants.getAll();
        List<String> allPlants = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allPlantMap.entrySet()) {
            JSONObject plantDetails = null;
            try {
                plantDetails = new JSONObject((String) entry.getValue());
            }
            catch (JSONException e) {
                continue;
            }
            try {
                String plantName = plantDetails.getString("name");
                allPlants.add(plantName);
            }
            catch (JSONException e) {
                continue;
            }
        }
        String[] allPlantsArray = allPlants.toArray(new String[0]);

        builder.setTitle("Add Event");
        final EditText editText = new EditText(this);
        editText.setHint("Event Title");
        // create a dropdownlist from the plants
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allPlants);

        Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        spinner.setAdapter(adapter);

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(spinner);
        layout.addView(editText);
        builder.setView(layout);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String eventTitle = editText.getText().toString();
                String plantName = spinner.getSelectedItem().toString();

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.TITLE, eventTitle);
                values.put(CalendarContract.Events.DESCRIPTION, "Description");
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Kolkata");
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                addEventToSharedPreferences(eventTitle, plantName);
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

    private void addEventToSharedPreferences(String eventTitle, String plantName) {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("date", getSelectedDateAsString());
            eventObject.put("title", eventTitle);
            eventObject.put("plantName", plantName);
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
                    String plantName = eventObject.getString("plantName");
                    String eventString = eventTitle + " (" + plantName + ")";
                    eventTitles.add(eventString);
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