package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.model.Item;
import com.codecool.notificationhelper.repository.CustomerRepository;
import com.codecool.notificationhelper.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String getItems(OAuth2Authentication authentication, ModelMap modelMap) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer != null) {
            List<Item> items = itemRepository.findAllByCustomerOrderByExpiryDateAsc(customer);

            modelMap.addAttribute("properties", properties);
            modelMap.addAttribute("items", items);

            return "items";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public String getItem(OAuth2Authentication authentication, ModelMap modelMap,
                          @PathVariable("id") long itemId) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);
        if (item == null) {
            return "redirect:/items";
        }

        modelMap.addAttribute("properties", properties);
        modelMap.addAttribute("item", item);

        return "item_details";
    }

}
