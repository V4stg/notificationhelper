package com.codecool.notificationhelper.model;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    private String googleId;
    private String email;

    public Customer() {
    }

    public Customer(String googleId, String email) {
        this.googleId = googleId;
        this.email = email;
    }
}
