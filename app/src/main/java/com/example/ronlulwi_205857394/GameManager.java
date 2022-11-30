package com.example.ronlulwi_205857394;

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

    public void setActiveEnemy(int row, int col) {
        activeEnemies.add(new Position(row, col));

    }

    public ArrayList<Position> calcNextMove(){
        if(activeEnemies.size() == ROWS){
            enemyLastLocation = activeEnemies.get(ROWS-1).getCol();
            activeEnemies.remove(ROWS-1);
            setScore(1);
        }
        //activeEnemies.trimToSize();
        activeEnemies.add(0, new Position(0, new Random().nextInt(3)));
        for (int i = 1; i < activeEnemies.size(); i++) {
            activeEnemies.get(i).setRow(1);
        }
        return activeEnemies;
    }


}
