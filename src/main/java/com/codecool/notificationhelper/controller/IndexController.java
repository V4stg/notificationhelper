package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class IndexController {

    private final CustomerRepository customerRepository;

    @Autowired
    public IndexController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String mainPage(OAuth2Authentication authentication, ModelMap modelMap, Principal principal) {

        if (authentication != null) {
            Object authDetails = authentication.getUserAuthentication().getDetails();
            @SuppressWarnings("unchecked")
            HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

            String googleId = (String) properties.get("id");
            Customer customer = customerRepository.findByGoogleId(googleId);

            modelMap.addAttribute("properties", properties);
            modelMap.addAttribute("principal", principal);

            if (customer != null) {
                UUID customerId = customer.getId();
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
