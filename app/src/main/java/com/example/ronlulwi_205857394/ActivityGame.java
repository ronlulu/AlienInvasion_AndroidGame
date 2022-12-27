package com.example.ronlulwi_205857394;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityGame extends AppCompatActivity {

    final int COLS = 5;
    final int ROWS = 6;
    int DELAY = 500;
    boolean isActiveSensors;
    public static final String KEY_DELAY = "KEY_DELAY";
    public static final String KEY_SENSOR = "KEY_SENSOR";
    private ExtendedFloatingActionButton game_BTN_right;
    private ExtendedFloatingActionButton game_BTN_left;
    private AppCompatImageView game_IMG_background;

    private ShapeableImageView[][] enemiesMat;
    private ShapeableImageView[] playerMat;
    private ShapeableImageView[] game_IMG_hearts;
    private ArrayList<Position> oldUI;

    private TextView game_LBL_score;
    private GameManager gameManager;
    private Timer timer;

    private MySensor mySensor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent previousIntent = getIntent();
        DELAY = previousIntent.getExtras().getInt(KEY_DELAY);
        isActiveSensors = previousIntent.getExtras().getBoolean(KEY_SENSOR);
        gameManager = new GameManager(ROWS, COLS, 3);
        findViews();
        Glide.with(ActivityGame.this)
                .load("https://images.unsplash.com/photo-1513002749550-c59d786b8e6c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80.jpg")
                        .into(game_IMG_background);
        initViews();
        timer = new Timer();

        mySensor = new MySensor(callBack_movement);
    }

    private MySensor.CallBack_movement callBack_movement = new MySensor.CallBack_movement() {
        @Override
        public void moveTo(int loc) {
            int playerCurrentCol = gameManager.getPlayerCurrentCol();
            if(playerCurrentCol != loc)
                if(playerCurrentCol < loc)
                    preformPlayerMove(1, COLS-1);
                else
                    preformPlayerMove(-1,0);
        }

        @Override
        public void setSpeed(int speed) {
            if(DELAY != speed){
                DELAY = speed;
            }
        }
    };

    private void findViews() {
        // find buttons + score + backGround

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        if(isActiveSensors){
            game_BTN_left.setVisibility(View.INVISIBLE);
            game_BTN_right.setVisibility(View.INVISIBLE);
        }

        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_IMG_background = findViewById(R.id.game_IMG_background);

        // find hearts
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart01),
                findViewById(R.id.main_IMG_heart02),
                findViewById(R.id.main_IMG_heart03) };

        // find game mat
        enemiesMat = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_enemy00), findViewById(R.id.game_IMG_enemy01), findViewById(R.id.game_IMG_enemy02), findViewById(R.id.game_IMG_enemy03), findViewById(R.id.game_IMG_enemy04)},
                {findViewById(R.id.game_IMG_enemy10), findViewById(R.id.game_IMG_enemy11), findViewById(R.id.game_IMG_enemy12), findViewById(R.id.game_IMG_enemy13), findViewById(R.id.game_IMG_enemy14)},
                {findViewById(R.id.game_IMG_enemy20), findViewById(R.id.game_IMG_enemy21), findViewById(R.id.game_IMG_enemy22), findViewById(R.id.game_IMG_enemy23), findViewById(R.id.game_IMG_enemy24)},
                {findViewById(R.id.game_IMG_enemy30), findViewById(R.id.game_IMG_enemy31), findViewById(R.id.game_IMG_enemy32), findViewById(R.id.game_IMG_enemy33), findViewById(R.id.game_IMG_enemy34)},
                {findViewById(R.id.game_IMG_enemy40), findViewById(R.id.game_IMG_enemy41), findViewById(R.id.game_IMG_enemy42), findViewById(R.id.game_IMG_enemy43), findViewById(R.id.game_IMG_enemy44)},
                {findViewById(R.id.game_IMG_enemy50), findViewById(R.id.game_IMG_enemy51), findViewById(R.id.game_IMG_enemy52), findViewById(R.id.game_IMG_enemy53), findViewById(R.id.game_IMG_enemy54)} };

        // find playerMat
        playerMat = new ShapeableImageView[] {findViewById(R.id.game_IMG_enemy60), findViewById(R.id.game_IMG_enemy61), findViewById(R.id.game_IMG_enemy62), findViewById(R.id.game_IMG_enemy63), findViewById(R.id.game_IMG_enemy64)};
    }

    private void initViews() {
        // init score
        game_LBL_score.setText("score: 0");

        // init left + right buttons
        if(!isActiveSensors) {
            game_BTN_left.setOnClickListener(view -> preformPlayerMove(-1, 0));
            game_BTN_right.setOnClickListener(view -> preformPlayerMove(1, COLS - 1));
        }

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
        int randomCol = new Random().nextInt(COLS);
        oldUI.add(new Position(0,randomCol, Position.types.UFO));
        gameManager.setActiveEnemy(0, randomCol, "UFO");
        enemiesMat[0][randomCol].setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        if(isActiveSensors)
            mySensor.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        if(isActiveSensors)
            mySensor.stop();
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new MyClass(), DELAY);

    }

    class MyClass extends TimerTask {
        public void run() {
            runOnUiThread(() -> updateUI());
            startTimer();
        }
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

        if(gameManager.getPlayerCurrentCol() == gameManager.getEnemyLastLocation()){
            if(oldUI.get(oldUI.size()-1).getType() == Position.types.COIN)
                gainCoin();
            else if(oldUI.get(oldUI.size()-1).getType() == Position.types.HEART)
                gainLives();
            else
                takeDamage();
        }

        for (int i = 0; i < newUI.size(); i++) {
            int row = newUI.get(i).getRow();
            int col = newUI.get(i).getCol();
            Position.types currentType = gameManager.getEnemyType(i);
            if(currentType == Position.types.HEART)
                enemiesMat[row][col].setImageResource(R.drawable.ic_heart);
            else if (currentType == Position.types.COIN)
                enemiesMat[row][col].setImageResource(R.drawable.ic_coin);
            else
                enemiesMat[row][col].setImageResource(R.drawable.ic_ufo);
            enemiesMat[row][col].setVisibility(View.VISIBLE);
        }
        oldUI.removeAll(oldUI);
        for (int i = 0; i < newUI.size(); i++) {
            oldUI.add(new Position(newUI.get(i).getRow(), newUI.get(i).getCol(), newUI.get(i).getType()));
        }
        game_LBL_score.setText("score: "+gameManager.getScore());


    }

    private void takeDamage() {
        int lives = gameManager.getLives();
        if(lives == 1)
            gameOver(gameManager.getScore());

        if(lives > 0){
            game_IMG_hearts[lives-1].setVisibility(View.INVISIBLE);
            gameManager.setLives(-1);
        }
        gameManager.playHitSound(Position.types.UFO);
        gameManager.vibrate();
        game_LBL_score.setText("score: " + gameManager.getScore());
        Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT).show();

    }

    private void gameOver(int score) {
        Intent intent = new Intent(this, ActivityScore.class);
        intent.putExtra(ActivityScore.KEY_SCORE, score);
        intent.putExtra(ActivityScore.KEY_LAST_CLASS_NAME, "ActivityGame");

        startActivity(intent);
        finish();
    }

    private void gainLives(){
        int lives = gameManager.getLives();
        if(lives < 3){
            gameManager.setLives(1);
            game_IMG_hearts[lives].setVisibility(View.VISIBLE);
        }
        gameManager.playHitSound(Position.types.HEART);
        Toast.makeText(this, "Good Job!", Toast.LENGTH_SHORT).show();
    }

    private void gainCoin() {
        gameManager.setScore(5);
        gameManager.playHitSound(Position.types.COIN);
        Toast.makeText(this, "You gained 5 coins!", Toast.LENGTH_SHORT).show();
    }

}