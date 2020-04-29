package com.example.quizapplication.model;

public class UserRank {
    private int score;
    private String username;
    int image;

    public UserRank(int score, String username) {
        this.score = score;
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
}
