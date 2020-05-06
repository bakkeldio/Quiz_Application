package com.example.quizapplication.model;

public class UserRank {
    private String score;
    private String username;

    public UserRank(String score, String username) {
        this.score = score;
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }
}
