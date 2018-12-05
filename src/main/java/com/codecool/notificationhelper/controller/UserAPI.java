package com.codecool.notificationhelper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserAPI {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
