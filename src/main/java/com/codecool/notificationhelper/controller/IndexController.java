package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@Controller
public class IndexController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String createUser(OAuth2Authentication authentication, ModelMap modelMap) {
        // TODO -> if user exists in database, return message about unsuccessful attempt.

        if (authentication != null) {
            HashMap<String, Object> properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

            String googleId = (String) properties.get("id");

            modelMap.addAttribute("properties", properties);

            Customer customer = customerRepository.findByGoogleId(googleId);

            if (customer != null) {
                int customerId = customer.getId();
                modelMap.addAttribute("customerId", customerId);
            }
        }

        return "index";
    }
}
