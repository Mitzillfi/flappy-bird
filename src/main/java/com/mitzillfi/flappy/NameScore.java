package com.mitzillfi.flappy;

/**
 * NameScore
 */
public class NameScore {
    private String name;
    private int score;

    public NameScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}