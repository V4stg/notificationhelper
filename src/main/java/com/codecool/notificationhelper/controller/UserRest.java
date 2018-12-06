package com.codecool.notificationhelper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRest {
// for developing and testing purposes

    /**
     * Sends only the necessary user information.
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        // TODO -> if user exists in database, log in.

        return principal;
    }
}
