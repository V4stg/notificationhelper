package com.codecool.notificationhelper.controller;

import com.codecool.notificationhelper.repository.CustomerRepository;
import com.codecool.notificationhelper.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

}
