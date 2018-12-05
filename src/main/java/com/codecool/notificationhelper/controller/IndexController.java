package com.codecool.notificationhelper.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String createUser(OAuth2Authentication authentication, ModelMap modelMap) {
        // TODO -> if user exists in database, return message about unsuccessful attempt.
        if (authentication != null) {
            HashMap<String, Object> properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();
            modelMap.addAttribute("name", properties.get("name"));
            modelMap.addAttribute("email", properties.get("email"));
            modelMap.addAttribute("img", properties.get("picture"));
            modelMap.addAttribute("properties", properties.toString());
        }

        // TODO -> else insert user data into database, and log in.

        return "index";
    }
}
