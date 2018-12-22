package com.codecool.notificationhelper.repository;

import com.codecool.notificationhelper.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Customer findByGoogleId(String id);

}
