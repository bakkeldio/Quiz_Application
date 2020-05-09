package com.example.quizapplication.model;

import java.util.ArrayList;

public class TestInfo {

    private String testName;

    private ArrayList<UserRank> users;

    public TestInfo(){

    }


    public TestInfo(String testName, ArrayList<UserRank> users) {
        this.testName = testName;
        this.users = users;
    }

    public String getTestName() {
        return testName;
    }

    public ArrayList<UserRank> getUsers() {
        return users;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setUsers(ArrayList<UserRank> users) {
        this.users = users;
    }
}
