package com.codecool.notificationhelper.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserAPI {

    /**
     * Validates, if user already exists, google account is already linked to a user.
     *
     * @param payload
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Map<String, Object> createUser(@RequestBody Map<String, Object> payload) throws Exception {
        // TODO -> if user exists in database, return message about unsuccessful attempt.
        // TODO -> else insert user data into database, and log in.

        return new HashMap<>();
    }

    /**
     * Sends only the necessary user information.
     *
     * @param payload
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, Object> getUser(@RequestBody Map<String, Object> payload) throws Exception {
        // TODO -> if user exists in database, log in.
        // TODO -> else return message about unsuccessful attempt.

        return new HashMap<>();
    }

    /**
     * Edits user information, preferences, if allowed.
     *
     * @param payload
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public Map<String, Object> editUser(@RequestBody Map<String, Object> payload) throws Exception {
        // TODO -> if allowed, edit user data, and return message about success.
        // TODO -> else return message about unsuccessful attempt.

        return new HashMap<>();
    }

    /**
     * Delete user account.
     *
     * @param payload
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public Map<String, Object> deleteUser(@RequestBody Map<String, Object> payload) throws Exception {
        // TODO -> if allowed, delete user data, and return message about success
        // TODO -> else return message about unsuccessful attempt.

        return new HashMap<>();
    }
}
