package com.codecool.notificationhelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

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

        HashMap<String, Object> response = new HashMap<>();
        return response;
    }

    /**
     * Sends only the necessary user information.
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal getUser(Principal principal, ModelMap modelMap) {
        // TODO -> if user exists in database, log in.
        return principal;
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