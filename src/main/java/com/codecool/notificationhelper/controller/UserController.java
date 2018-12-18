package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.repository.CustomerRepository;
import com.codecool.notificationhelper.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createUser(OAuth2Authentication authentication) {

        if (authentication != null) {
            HashMap<String, Object> properties;
            properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

            String googleId = (String) properties.get("id");
            String name = (String) properties.get("name");
            String email = (String) properties.get("email");

            Customer customer = customerRepository.findByGoogleId(googleId);

            if (customer == null) {
                customerRepository.save(new Customer(googleId, email));
            }
        }

        return "redirect:/index";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(OAuth2Authentication authentication, ModelMap modelMap) {

        if (authentication != null) {
            HashMap<String, Object> properties;
            properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

            String googleId = (String) properties.get("googleId");
            String name = (String) properties.get("name");
            String email = (String) properties.get("email");
            String picture = (String) properties.get("picture");

            modelMap.addAttribute("googleId", googleId);
            modelMap.addAttribute("name", name);
            modelMap.addAttribute("email", email);
            modelMap.addAttribute("picture", picture);

            return "customer";
        }

        return "redirect:/index";
    }
}
