package com.example.prayercards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import Models.Prayer;

/*
    This activity displays a particular prayer: the day, prayer, and the takenFrom
 */

public class PrayerContent extends AppCompatActivity {
    private TextView txt_day, txt_prayer, txt_taken_from;
    private Button btn_back;
    private Prayer prayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_content);

        // Set the status bar color to blue
        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));

        txt_day = findViewById(R.id.txt_day);
        txt_prayer = findViewById(R.id.txt_prayer);
        txt_taken_from = findViewById(R.id.txt_taken_from);

        // Get the prayer details from intent data coming from the previous activity that call this activity
        Intent prayerIntent = getIntent();
        prayer = new Prayer();
        prayer.setDay(prayerIntent.getIntExtra("day", 0));
        prayer.setPrayer(prayerIntent.getStringExtra("prayer"));
        prayer.setTakenFrom(prayerIntent.getStringExtra("takenFrom"));

        // Set the prayer details to the TextViews
        txt_day.setText("Day " + prayer.getDay());
        txt_prayer.setText(prayer.getPrayer());
        txt_taken_from.setText(prayer.getTakenFrom());

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}