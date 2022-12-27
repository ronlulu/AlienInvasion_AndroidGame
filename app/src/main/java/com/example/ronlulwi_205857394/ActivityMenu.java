package com.example.ronlulwi_205857394;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class ActivityMenu extends AppCompatActivity {

    private AppCompatImageView menu_IMG_background;
    private MaterialButton menu_BTN_fast, menu_BTN_slow, menu_BTN_sensors, menu_BTN_top10, menu_BTN_exitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        askGpsPermission();
        findViews();
        Glide.with(ActivityMenu.this)
                .load("https://wallpapers.com/images/hd/alien-ship-on-the-mountain-ua912nnxda1wazx2.webp")
                .into(menu_IMG_background);
        initViews();
    }

    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_fast = findViewById(R.id.menu_BTN_fast);
        menu_BTN_slow= findViewById(R.id.menu_BTN_slow);
        menu_BTN_sensors= findViewById(R.id.menu_BTN_sensors);
        menu_BTN_top10= findViewById(R.id.menu_BTN_top10);
        menu_BTN_exitGame= findViewById(R.id.menu_BTN_exitGame);

    }

    private void initViews() {

        menu_BTN_slow.setOnClickListener(view -> openGamePage(600, false));
        menu_BTN_fast.setOnClickListener(view -> openGamePage(300, false));
        menu_BTN_sensors.setOnClickListener(view -> openGamePage(500, true));
        menu_BTN_top10.setOnClickListener(view -> openScorePage());
        menu_BTN_exitGame.setOnClickListener(view -> finish());
    }

    private void openScorePage() {
        Intent intent = new Intent(this, ActivityScore.class);
        intent.putExtra(ActivityScore.KEY_LAST_CLASS_NAME, "ActivityMenu");
        startActivity(intent);
        finish();
    }

    private void openGamePage(int delay, Boolean sensors) {
        Intent intent = new Intent(this, ActivityGame.class);
        intent.putExtra(ActivityGame.KEY_DELAY, delay);
        intent.putExtra(ActivityGame.KEY_SENSOR, sensors);
        startActivity(intent);
        finish();
    }

    private void askGpsPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
}