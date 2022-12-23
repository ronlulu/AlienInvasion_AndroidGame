package com.example.ronlulwi_205857394;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ActivityScore extends AppCompatActivity {

    private FragmentList fragment_list;
    private FragmentMap fragment_map;
    private ExtendedFloatingActionButton score_BTN_exitGame, score_BTN_newGame;

    CallBack_UserProtocol callBack_userProtocol = new CallBack_UserProtocol() {
        @Override
        public void displayOnMap() {
            showUserLocation();
        }
    };

    private void showUserLocation() {
        //TODO get user lat&lon from shared preferences

        double lat = 30.99;
        double lon = 32.67;
        fragment_map.zoom(lat, lon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        initViews();


        fragment_list.setCallBack_userProtocol(callBack_userProtocol);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map, fragment_map).commit();
    }

    private void initViews() {
        fragment_list = new FragmentList();
        fragment_map = new FragmentMap();
        score_BTN_newGame.setOnClickListener(view -> openGameMenu());
        score_BTN_exitGame.setOnClickListener(view -> finish());
    }

    private void openGameMenu() {
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        score_BTN_exitGame = findViewById(R.id.score_BTN_exitGame);
        score_BTN_newGame = findViewById(R.id.score_BTN_newGame);
    }


}