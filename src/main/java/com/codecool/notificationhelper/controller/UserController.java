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
public class UserController {

    private final CustomerRepository customerRepository;

    @Autowired
    public UserController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createUser(OAuth2Authentication authentication, ModelMap modelMap) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        String email = (String) properties.get("email");

        modelMap.addAttribute("properties", properties);

        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            customerRepository.save(new Customer(googleId, email));
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(OAuth2Authentication authentication, ModelMap modelMap) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        modelMap.addAttribute("properties", properties);
        return "customer";
    }
}
