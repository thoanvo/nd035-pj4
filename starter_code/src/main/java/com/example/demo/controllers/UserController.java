package com.example.demo.controllers;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
	logger.info("Find user id: " + id);
	try {
	    User user = userRepository.findById(id)
		    .orElseThrow(() -> new EntityNotFoundException("Not found id: " + id));
	    return ResponseEntity.ok(user);
	} catch (EntityNotFoundException e) {
	    logger.error("Not found id: " + id);
	    return ResponseEntity.notFound().build();
	}
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
	logger.info("Find user name: " + username);
	User user = userRepository.findByUsername(username);
	return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
	User user = new User();
	logger.info("Username is " + createUserRequest.getUsername());
	user.setUsername(createUserRequest.getUsername());
	Cart cart = new Cart();
	cartRepository.save(cart);
	user.setCart(cart);

	if (createUserRequest.getPassword().length() < 6
		|| !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
	    String msg = "Error - Either length is less than 6 or pass and confirm pass do not match. Unable to create";
	    logger.info(msg);
	    return ResponseEntity.badRequest().build();
	}
	user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

	userRepository.save(user);
	logger.info("User created successfully");
	return ResponseEntity.ok(user);
    }

}
