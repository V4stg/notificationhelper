package com.codecool.notificationhelper.repository;

import com.codecool.notificationhelper.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByGoogleId(String id);

}
