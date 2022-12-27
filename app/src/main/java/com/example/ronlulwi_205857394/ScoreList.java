package com.example.ronlulwi_205857394;

import java.util.ArrayList;

public class ScoreList {

    private ArrayList<Score> scores = new ArrayList<>();

    public ScoreList() {
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ScoreList setScores(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                "scores=" + scores +
                '}';
    }
}
