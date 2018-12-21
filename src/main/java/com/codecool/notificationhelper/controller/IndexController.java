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

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String mainPage(OAuth2Authentication authentication, ModelMap modelMap) {

        if (authentication != null) {
            HashMap<String, Object> properties;
            properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

            String googleId = (String) properties.get("id");
            Customer customer = customerRepository.findByGoogleId(googleId);

            modelMap.addAttribute("properties", properties);

            if (customer != null) {
                long customerId = customer.getId();
                modelMap.addAttribute("customerId", customerId);
            }
        }

        return "index";
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String indexRedirect() {
        return "redirect:/";
    }
}
