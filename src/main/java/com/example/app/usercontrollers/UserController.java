package com.example.app.usercontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.UserDAOs.UserDAO;
import com.example.app.entities.User;
import com.example.app.userservices.userservicecontract;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	public userservicecontract userService;
	@Autowired
	public UserController(userservicecontract userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity <?> registerUser(@RequestBody User user) {
	try {
	User registeredUser = userService.registerUser(user);
	return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", new UserDAO(registeredUser.getUserId(),registeredUser.getUsername(),registeredUser.getEmail(),registeredUser.getRole().toString())));
	} catch (RuntimeException e) {
	return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	}
}
}
