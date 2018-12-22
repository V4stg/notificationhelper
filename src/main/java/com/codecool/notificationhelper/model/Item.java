package com.codecool.notificationhelper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer customer;

    private String name;

    private String description;

    private Date expiryDate;
    private Date notificationDate;

    private boolean sendEmail;

    public Item() {
    }

    public Item(Customer customer, String name) {
        this.customer = customer;
        this.name = name;
    }

    public Item(Customer customer, String name, Date expiryDate) {
        this.customer = customer;
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
