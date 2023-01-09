package com.example.ronlulwi_205857394;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreManager {
    private static final String SP_KEY_SCORES = "SP_KEY_SCORES";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Context myContext;
    private LocationManager locationManager;
    public Comparator<Score> comparator;


    public ScoreManager(Context context) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.myContext = context;

        comparator = new Comparator<Score>() {
            @Override
            public int compare(Score a, Score b) {
                return b.getScore() - a.getScore();
            }
        };
    }

    public boolean checkScore(ScoreList scoreList, int score) {
        boolean listHasChanged = false;
        if (scoreList.getScores().size() < 10) {
            updateScore(scoreList, score);
            listHasChanged = true;
        } else {
            Score minCurrentScore = getMinScore(scoreList);
            int minCurrentScoreIndex = scoreList.getScores().indexOf(minCurrentScore);
            if (minCurrentScore.getScore() < score) {
                updateScoreAt(scoreList, minCurrentScoreIndex, score);
                listHasChanged = true;
            }
        }
        return listHasChanged;
    }

    public void updateScoreAt(ScoreList scoreList, int index, int score) {
        if (score == 0)
            return;
        Location loc = getUserLocation(myContext);
        if(loc !=null){
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            scoreList.getScores().get(index).setScore(score).setLat(lat).setLon(lon);
            Collections.sort(scoreList.getScores(), comparator);
        }
    }

    public void updateScore(ScoreList scoreList, int score) {
        if (score == 0)
            return;
        Location loc = getUserLocation(myContext);
        if(loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Score newScore = new Score()
                    .setScore(score)
                    .setLat(lat)
                    .setLon(lon);
            scoreList.getScores().add(newScore);
            Collections.sort(scoreList.getScores(), comparator);
        }
    }

    public ScoreList getTopScoresFromSP() {
        String scoreListAsJsonStringFromSP = MySharedPreferences.getInstance().getString(SP_KEY_SCORES, "");
        if (scoreListAsJsonStringFromSP.equals(""))
            return null;
        ScoreList scoreListFromSP = new Gson().fromJson(scoreListAsJsonStringFromSP, ScoreList.class);
        return scoreListFromSP;
    }

    public void putTopScoresToSP(ScoreList scoreList) {
        String scoreListJson = new Gson().toJson(scoreList);
        MySharedPreferences.getInstance().putString(SP_KEY_SCORES, scoreListJson);
    }

    private Score getMinScore(ScoreList scoreList) {
        ArrayList<Score> scores = scoreList.getScores();
        Score minScore = scores.get(0);
        for (int i = 1; i < scores.size(); i++) {
            if (minScore.getScore() > scores.get(i).getScore())
                minScore = scores.get(i);
        }
        return minScore;
    }

    private Location getUserLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return null;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return null;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            return lastKnownLocation;
        } else {
            return null;
        }
    }

}
