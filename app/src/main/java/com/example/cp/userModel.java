package com.example.cp;

public class userModel {
    int id;
    String complain;

    public userModel(int id, String complain) {
        this.id = id;
        this.complain = complain;

    }

    public int getId() {
        return id;
    }

    public String getComplain() {
        return complain;
    }
}
