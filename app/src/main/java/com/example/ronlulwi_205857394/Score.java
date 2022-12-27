package com.example.ronlulwi_205857394;

public class Score {
    private int score;
    private double lat;
    private double lon;

    public Score() {}

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Score setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Score setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
