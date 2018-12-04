package com.codecool.notificationhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NotificationHelperApp {

    public static void main(String[] args) {
        SpringApplication.run(NotificationHelperApp.class, args);
    }
}
