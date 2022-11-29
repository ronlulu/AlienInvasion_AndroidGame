package com.example.ronlulwi_205857394;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame extends AppCompatActivity {

    final int COLS = 3;
    final int ROWS = 4;
    final int DELAY = 1000;

    private ExtendedFloatingActionButton game_BTN_right;
    private ExtendedFloatingActionButton game_BTN_left;
    private AppCompatImageView game_IMG_background;

    private ShapeableImageView[][] enemiesMat;
    private ShapeableImageView[] playerMat;
    private ShapeableImageView[] game_IMG_hearts;
    private ArrayList<Position> oldUI;

    private TextInputEditText game_LBL_score;
    private GameManager gameManager;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameManager = new GameManager(ROWS, COLS, 3);
        findViews();
        Glide.with(ActivityGame.this)
                .load("https://images.unsplash.com/photo-1513002749550-c59d786b8e6c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80.jpg")
                        .into(game_IMG_background);
        initViews();
        timer = new Timer();
    }

    private void findViews() {
        // find buttons + score + backGround
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_IMG_background = findViewById(R.id.game_IMG_background);

        // find hearts
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart01),
                findViewById(R.id.main_IMG_heart02),
                findViewById(R.id.main_IMG_heart03) };

        // find game mat
        enemiesMat = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_enemy00), findViewById(R.id.game_IMG_enemy01), findViewById(R.id.game_IMG_enemy02)},
                {findViewById(R.id.game_IMG_enemy10), findViewById(R.id.game_IMG_enemy11), findViewById(R.id.game_IMG_enemy12)},
                {findViewById(R.id.game_IMG_enemy20), findViewById(R.id.game_IMG_enemy21), findViewById(R.id.game_IMG_enemy22)},
                {findViewById(R.id.game_IMG_enemy30), findViewById(R.id.game_IMG_enemy31), findViewById(R.id.game_IMG_enemy32)}};

        // find playerMat
        playerMat = new ShapeableImageView[] {findViewById(R.id.game_IMG_enemy40), findViewById(R.id.game_IMG_enemy41), findViewById(R.id.game_IMG_enemy42)};
    }

    private void initViews() {
        // init score
        game_LBL_score.setText("score: 0");

        // init left + right buttons
        game_BTN_left.setOnClickListener(view -> preformPlayerMove(-1, 0));
        game_BTN_right.setOnClickListener(view -> preformPlayerMove(1, COLS-1));

        // init playerMat
        for (int i = 0; i < playerMat.length; i++) {
            playerMat[i].setVisibility(View.INVISIBLE);
            if(i== 1)
                playerMat[i].setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                enemiesMat[i][j].setVisibility(View.INVISIBLE);
            }
        }

        // create first UFO
        createFirstUFO();
    }

    private void preformPlayerMove(int diff, int bound) {
        int playerCurrentCol = gameManager.getPlayerCurrentCol();
        if(playerCurrentCol != bound){
            playerMat[playerCurrentCol].setVisibility(View.INVISIBLE);
            playerMat[playerCurrentCol + diff].setVisibility(View.VISIBLE);
            gameManager.setPlayerCurrentCol(diff);
        }

    }

    private void createFirstUFO() {
        oldUI = new ArrayList<>();
        int randomCol = new Random().nextInt(3);
        oldUI.add(new Position(0,randomCol));
        gameManager.setActiveEnemy(0, randomCol);
        enemiesMat[0][randomCol].setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateUI());
            }
        }, DELAY, DELAY);
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void updateUI() {
        ArrayList<Position> newUI = gameManager.calcNextMove();

        for (int i = 0; i < oldUI.size(); i++) {
            int row = oldUI.get(i).getRow();
            int col = oldUI.get(i).getCol();
            enemiesMat[row][col].setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < newUI.size(); i++) {
            int row = newUI.get(i).getRow();
            int col = newUI.get(i).getCol();
            enemiesMat[row][col].setVisibility(View.VISIBLE);
        }
        oldUI.removeAll(oldUI);
        for (int i = 0; i < newUI.size(); i++) {
            oldUI.add(new Position(newUI.get(i).getRow(), newUI.get(i).getCol()));
        }
        game_LBL_score.setText("score: "+gameManager.getScore());

        if(gameManager.getPlayerCurrentCol() == gameManager.getEnemyLastLocation())
            takeDamage();
    }

    private void takeDamage() {
        int lives = gameManager.getLives();
        if(lives > 0){
            game_IMG_hearts[lives-1].setVisibility(View.INVISIBLE);
            gameManager.setLives(-1);
        }
        vibrate();
        gameManager.setScore(-1);
        game_LBL_score.setText("score: "+gameManager.getScore());
        Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT).show();

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }


    }

}