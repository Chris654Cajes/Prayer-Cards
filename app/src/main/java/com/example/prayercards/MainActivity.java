package com.example.prayercards;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Adapters.PrayerAdapter;
import Models.Prayer;

/*
    This activity displays all the prayers with button for each prayer to display a particular prayer
*/

public class MainActivity extends AppCompatActivity {
    private Button btn_about;
    private RecyclerView recyclerView;
    private PrayerAdapter adapter;
    private ArrayList<Prayer> prayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the status bar color to blue
        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));

        prayers = new ArrayList<Prayer>();

        loadJsonPrayers(); // Get all the prayers for display

        adapter = new PrayerAdapter(MainActivity.this, prayers); // Display all prayers to add them to buttons for each in the list

        // Put all prayers list to recyclerView using the adapter as blueprints
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btn_about = findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutPage.class);
                startActivity(intent);
            }
        });
    }

    // This method gets all the data from the JSON file and add them to prayers list
    private void loadJsonPrayers() {
        try {
            // Filename of the JSON file to be fetched
            InputStream inputStream = getAssets().open("data.json");

            // get the size of the array from JSON file
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            // Read the JSON file contents and add to buffer
            inputStream.read(buffer);
            inputStream.close();

            String json;
            int day;
            String prayer, takenFrom;

            // Get content from the fetched JSON from the JSON file and add to JSON string and convert to JSONArray
            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            // Get every JSON item from JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Prayer prayerItem = new Prayer();

                prayerItem.setDay(jsonObject.getInt("day"));
                prayerItem.setPrayer(jsonObject.getString("prayer"));
                prayerItem.setTakenFrom(jsonObject.getString("takenFrom"));

                prayers.add(prayerItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}