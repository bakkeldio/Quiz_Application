package com.example.quizapplication.model;

public class UserRank {
    private String testName;
    private int image;

    public UserRank(String testName, int image) {
        this.testName = testName;
        this.image = image;
    }

    public String getTestName() {
        return testName;
    }

    public int getImage() {
        return image;
    }
}
