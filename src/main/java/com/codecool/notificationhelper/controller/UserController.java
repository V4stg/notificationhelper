package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Validates, if user already exists, google account is already linked to a user.
     *
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createUser(OAuth2Authentication authentication) {
        // TODO -> if user exists in database, return message about unsuccessful attempt.
        // TODO -> else insert user data into database, and log in.

        if (authentication != null) {
            HashMap<String, Object> properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

            String googleId = (String) properties.get("id");
            String email = (String) properties.get("email");

            Customer customer = customerRepository.findByGoogleId(googleId);

            if (customer == null) {
                customerRepository.save(new Customer(googleId, email));
            }
        }

        return "redirect:/index";
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
