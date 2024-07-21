package com.example.cp;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
