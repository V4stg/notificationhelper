package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.model.Item;
import com.codecool.notificationhelper.repository.CustomerRepository;
import com.codecool.notificationhelper.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
                            @PathVariable("id") UUID itemId) {

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

    @RequestMapping(value = "/newitem", method = RequestMethod.GET)
    public String newItem(OAuth2Authentication authentication, ModelMap modelMap) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        modelMap.addAttribute("properties", properties);

        return "item_form";
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String createItem(OAuth2Authentication authentication,
                             @RequestParam("newItemName") String newItemName,
                             @RequestParam("newItemDescription") String newItemDescription,
                             @RequestParam(value = "newItemSendEmail", required = false) String newItemSendEmail) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        if (newItemName != "") {
            Item newItem = new Item(customer, newItemName, newItemDescription, newItemSendEmail != null);
            itemRepository.save(newItem);
            return "redirect:/item/" + newItem.getId();
        }

        return "redirect:/items";
    }

    @RequestMapping(value = "/item/{id}/delete", method = RequestMethod.GET)
    public String deleteItem(OAuth2Authentication authentication,
                             @PathVariable("id") UUID itemId) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);

        if (item != null) {
            itemRepository.delete(item);
        }

        return "redirect:/items";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.GET)
    public String editItemPage(OAuth2Authentication authentication, ModelMap modelMap,
                             @PathVariable("id") UUID itemId) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);

        if (item != null) {
            modelMap.addAttribute("properties", properties);
            modelMap.addAttribute("item", item);

            return "item_edit_form";
        }

        return "redirect:/items";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.POST)
    public String updateItem(OAuth2Authentication authentication,
                             @PathVariable("id") UUID itemId,
                             @RequestParam("newItemName") String newItemName,
                             @RequestParam("newItemDescription") String newItemDescription,
                             @RequestParam(value = "newItemSendEmail", required = false) String newItemSendEmail) {

        HashMap<String, Object> properties;
        properties = (HashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) {
            return "redirect:/";
        }

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);

        if (item != null && !newItemName.equals("")) {
            item.setName(newItemName);
            item.setDescription(newItemDescription);
            item.setSendEmail(newItemSendEmail != null);
            itemRepository.save(item);
        }

        return "redirect:/item/" + item.getId();
    }

}
