package com.murates.durumcumuratusta.dto;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name;
    public String email;
    public String password;


    public User() {
    }

    public User(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password=password;
    }
}