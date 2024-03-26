package com.example.demo.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
	logger.info("Get all items");
	List<Item> itemList = itemRepository.findAll();
	if (itemList.isEmpty()) {
	    logger.info("Item list is empty!");
	}
	return ResponseEntity.ok(itemList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
	logger.info("Get item id: " + id);
	try {
	    Item item = itemRepository.findById(id)
		    .orElseThrow(() -> new EntityNotFoundException("Not found id: " + id));
	    return ResponseEntity.ok(item);
	} catch (EntityNotFoundException e) {
	    logger.error("Not found id: " + id);
	    return ResponseEntity.notFound().build();
	}
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
	List<Item> items = itemRepository.findByName(name);
	if (items == null || items.isEmpty()) {
	    logger.error("Item " + name + " not found!");
	    return ResponseEntity.notFound().build();
	} else {
	    logger.info("Get Item success");
	    return ResponseEntity.ok(items);
	}
    }

}
