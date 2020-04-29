package com.example.quizapplication.model;

public class DataModel {
    private String name;

    private int id;
    int image;

    public DataModel(String name, int id, int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }
    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

}
