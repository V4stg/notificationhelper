package com.codecool.notificationhelper.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserREST {

    @RequestMapping("/customer")
    public Principal user(OAuth2Authentication authentication) {
        return authentication;
    }
}
