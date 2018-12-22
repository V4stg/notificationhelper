package com.codecool.notificationhelper.repository;

import com.codecool.notificationhelper.model.Customer;
import com.codecool.notificationhelper.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findAllByCustomerOrderByExpiryDateAsc(Customer customer);
    List<Item> findAllByCustomer(Customer customer);
    Item findByIdAndAndCustomer(UUID id, Customer customer);

}
