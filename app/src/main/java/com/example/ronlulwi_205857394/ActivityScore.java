package com.example.ronlulwi_205857394;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ActivityScore extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    private int lastScore;
    private FragmentList fragment_list;
    private FragmentMap fragment_map;
    private MaterialTextView score_LBL_lastScore;
    private ExtendedFloatingActionButton score_BTN_exitGame, score_BTN_newGame;

    CallBack_UserProtocol callBack_userProtocol = new CallBack_UserProtocol() {
        @Override
        public void displayOnMap() {
            double lat = 30.99;
            double lon = 32.67;
            fragment_map.zoom(lat, lon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent previousIntent = getIntent();
        Class<?> last = previousIntent.getClass();
        if(last == ActivityGame.class)
            lastScore = previousIntent.getExtras().getInt(KEY_SCORE);

        findViews();
        initViews();
        fragment_list.setCallBack_userProtocol(callBack_userProtocol);

    }

    private void findViews() {
        score_BTN_exitGame = findViewById(R.id.score_BTN_exitGame);
        score_BTN_newGame = findViewById(R.id.score_BTN_newGame);
        score_LBL_lastScore = findViewById(R.id.score_LBL_lastScore);
    }

    private void initViews() {
        fragment_list = new FragmentList();
        fragment_map = new FragmentMap();
        score_LBL_lastScore.setText("YOUR SCORE IS: "+lastScore);
        score_BTN_newGame.setOnClickListener(view -> openGameMenu());
        score_BTN_exitGame.setOnClickListener(view -> finish());

        // init map fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.panel_LAY_map,fragment_map).commit();
        // init Scores fragment
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();

    }

    private void openGameMenu() {
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
        finish();
    }

}