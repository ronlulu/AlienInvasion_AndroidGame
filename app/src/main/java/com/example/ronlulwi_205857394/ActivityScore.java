package com.example.ronlulwi_205857394;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class ActivityScore extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String KEY_LAST_CLASS_NAME = "KEY_LAST_CLASS_NAME";

    private int lastScore;
    private FragmentList fragment_list;
    private FragmentMap fragment_map;
    private MaterialTextView score_LBL_lastScore;
    private ExtendedFloatingActionButton score_BTN_exitGame, score_BTN_newGame;
    private ScoreManager scoreManager;
    private ScoreList scoreList;
    private AppCompatImageView score_IMG_background;




    CallBack_UserProtocol callBack_userProtocol = new CallBack_UserProtocol() {
        @Override
        public void displayOnMap(int index) {
            double lat = scoreList.getScores().get(index).getLat();
            double lon = scoreList.getScores().get(index).getLon();
            fragment_map.zoom(lat, lon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        Intent previousIntent = getIntent();
        if("ActivityGame".equals(previousIntent.getExtras().getString(KEY_LAST_CLASS_NAME)))
            lastScore = previousIntent.getExtras().getInt(KEY_SCORE);

        findViews();
        initViews();

        Glide.with(ActivityScore.this)
                .load("https://w0.peakpx.com/wallpaper/976/355/HD-wallpaper-alien-alien-art-cartoon-peace.jpg")
                .into(score_IMG_background);
        initViews();


            scoreList = scoreManager.getTopScoresFromSP();
            if (scoreList == null) {
                scoreList = new ScoreList();
                scoreManager.updateScore(scoreList, lastScore);
                scoreManager.putTopScoresToSP(scoreList);
            } else if (scoreManager.checkScore(scoreList, lastScore))
                scoreManager.putTopScoresToSP(scoreList);

        fragment_list.setCallBack_userProtocol(callBack_userProtocol);
    }

    private void findViews() {
        score_IMG_background = findViewById(R.id.score_IMG_background);
        score_BTN_exitGame = findViewById(R.id.score_BTN_exitGame);
        score_BTN_newGame = findViewById(R.id.score_BTN_newGame);
        score_LBL_lastScore = findViewById(R.id.score_LBL_lastScore);
    }

    private void initViews() {
        fragment_list = new FragmentList();
        fragment_list.setActivity(this);
        fragment_map = new FragmentMap();
        scoreManager = new ScoreManager(this);
        if(lastScore == 0)
            score_LBL_lastScore.setText("TOP 10 SCORES!");
        else
            score_LBL_lastScore.setText("YOUR SCORE IS: " + lastScore);
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

    public ScoreList getScoreList() {
        return scoreList;
    }
}