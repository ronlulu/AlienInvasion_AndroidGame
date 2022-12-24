package com.example.ronlulwi_205857394;

public class ScoreData {
    private int score;
    private double lat;
    private double lon;

    public ScoreData() {}

    public int getScore() {
        return score;
    }

    public ScoreData setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public ScoreData setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public ScoreData setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "score=" + score +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
