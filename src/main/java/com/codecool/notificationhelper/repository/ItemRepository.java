package com.codecool.notificationhelper.repository;

import com.codecool.notificationhelper.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
