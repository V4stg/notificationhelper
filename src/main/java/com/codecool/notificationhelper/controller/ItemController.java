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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ItemController {

    private final CustomerRepository customerRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String getItems(OAuth2Authentication authentication, ModelMap modelMap) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        List<Item> items = itemRepository.findAllByCustomerOrderByExpiryDateAsc(customer);

        modelMap.addAttribute("properties", properties);
        modelMap.addAttribute("items", items);

        return "items";
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public String getItem(OAuth2Authentication authentication, ModelMap modelMap,
                            @PathVariable("id") UUID itemId) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

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

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        modelMap.addAttribute("properties", properties);

        return "item_form";
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String createItem(OAuth2Authentication authentication,
                             @RequestParam("newItemName") String newItemName,
                             @RequestParam("newItemDescription") String newItemDescription,
                             @RequestParam(value = "newItemExpiryDate") String expiryDateString,
                             @RequestParam(value = "newItemSendEmail", required = false) String newItemSendEmail) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        if (!newItemName.equals("")) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

// Java 8
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
//            LocalDate date = LocalDate.parse(string, formatter);

            Date expiryDate = null;
            try {
                expiryDate = format.parse(expiryDateString);

            } catch (ParseException e) {
//                e.printStackTrace();
            }

            Item newItem = new Item(customer, newItemName, newItemDescription, newItemSendEmail != null, expiryDate);
            itemRepository.save(newItem);
            return "redirect:/item/" + newItem.getId();
        }

        return "redirect:/items";
    }

    @RequestMapping(value = "/item/{id}/delete", method = RequestMethod.GET)
    public String deleteItem(OAuth2Authentication authentication,
                             @PathVariable("id") UUID itemId) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);

        if (item != null) {
            itemRepository.delete(item);
        }

        return "redirect:/items";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.GET)
    public String editItemPage(OAuth2Authentication authentication, ModelMap modelMap,
                             @PathVariable("id") UUID itemId) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

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
                             @RequestParam(value = "newItemExpiryDate") String expiryDateString,
                             @RequestParam(value = "newItemSendEmail", required = false) String newItemSendEmail) {

        Object authDetails = authentication.getUserAuthentication().getDetails();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> properties = (HashMap<String, Object>) authDetails;

        String googleId = (String) properties.get("id");
        Customer customer = customerRepository.findByGoogleId(googleId);

        if (customer == null) return "redirect:/";

        Item item = itemRepository.findByIdAndAndCustomer(itemId, customer);

        if (item != null && !newItemName.equals("")) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Date expiryDate = null;
            try {
                expiryDate = format.parse(expiryDateString);
            } catch (ParseException e) {
//                e.printStackTrace();
            }

            item.setName(newItemName);
            item.setDescription(newItemDescription);
            item.setSendEmail(newItemSendEmail != null);
            item.setExpiryDate(expiryDate);
            itemRepository.save(item);
        }

        if (item != null) return "redirect:/item/" + item.getId();
        else return "redirect:/items";
    }

}
