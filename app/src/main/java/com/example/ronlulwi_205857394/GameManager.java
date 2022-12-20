package com.example.ronlulwi_205857394;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private ArrayList<Position> activeEnemies;
    private int ROWS;
    private int COLS;
    private int lives;
    private int playerCurrentCol;
    private int score;
    private int enemyLastLocation;
    BackgroundSound mBackgroundSound;


    public GameManager(int rows,int cols, int lives) {
        this.ROWS = rows;
        this.COLS = cols;
        this.activeEnemies = new ArrayList<>();
        this.lives = lives;
        playerCurrentCol = 1;
        enemyLastLocation =-1;
        score = 0;
    }

    public int getEnemyLastLocation() {
        return enemyLastLocation;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score += score;
    }

    public int getPlayerCurrentCol() {
        return playerCurrentCol;
    }
    public void setPlayerCurrentCol(int playerCurrentCol) {
        this.playerCurrentCol += playerCurrentCol;
    }

    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives += lives;
    }

    public void setActiveEnemy(int row, int col, String type) {
        activeEnemies.add(new Position(row, col, Position.types.valueOf(type)));

    }

    public ArrayList<Position> calcNextMove(){
        if(activeEnemies.size() == ROWS){
            enemyLastLocation = activeEnemies.get(ROWS-1).getCol();
            activeEnemies.remove(ROWS-1);
            setScore(1);
        }
        if(score%7 == 0 && score > 7)
            if(lives < 3)
                activeEnemies.add(0, new Position(0, new Random().nextInt(COLS), Position.types.HEART));
            else
                activeEnemies.add(0, new Position(0, new Random().nextInt(COLS), Position.types.COIN));
        else
            activeEnemies.add(0, new Position(0, new Random().nextInt(COLS), Position.types.UFO));

        for (int i = 1; i < activeEnemies.size(); i++) {
            activeEnemies.get(i).setRow(1);
        }
        return activeEnemies;
    }

    public void playHitSound(Position.types type) {
        mBackgroundSound = new BackgroundSound();
        mBackgroundSound.execute(type);
    }

    public void vibrate() {
        Vibrator v = (Vibrator) MyApp.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }


    }

    public Position.types getEnemyType(int index) {
        return activeEnemies.get(index).getType();
    }

    public class BackgroundSound extends AsyncTask<Position.types, Void, Void> {

        @Override
        protected Void doInBackground(Position.types... params) {
            Position.types myTypes = params[0];
            MediaPlayer player;
            if(myTypes == Position.types.UFO)
                player = MediaPlayer.create(MyApp.getAppContext(), R.raw.msc_crash_sound);
            else if(myTypes == Position.types.COIN)
                player = MediaPlayer.create(MyApp.getAppContext(), R.raw.msc_coin_sound);
            else
                player = MediaPlayer.create(MyApp.getAppContext(), R.raw.msc_coin_sound);
            player.setLooping(false); // Set looping
            player.setVolume(4.0f, 4.0f);
            player.start();
            return null;
        }

    }



}
