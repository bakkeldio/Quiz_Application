package com.example.quizapplication;

public class AppUser {
    private String  id;
    private String username;
    private String email;
    private String phoneNumber;
    public AppUser(String id, String username, String email, String phoneNumber){
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
