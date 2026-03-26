package com.example.app.userimplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.app.entities.User;
import com.example.app.userrepositories.UserRepository;
import com.example.app.userservices.userservicecontract;
@Service
public class userservice implements userservicecontract{
	private UserRepository userRepo;
	private BCryptPasswordEncoder cryptPasswordEncoder;
	
	
	
	@Autowired
	public userservice(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
		this.cryptPasswordEncoder= new BCryptPasswordEncoder();
	}
	




	@Override
	public User registerUser(User user) {
		// Check if username or email already exists
		if (userRepo.findByUsername(user.getUsername()).isPresent()) {
		throw new RuntimeException("Username is already taken");

		}

		if (userRepo.findByEmail(user.getEmail()).isPresent()) {
		throw new RuntimeException("Email is already registered");

		}

		// Encode password before saving
		user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));

		// Save the user
		return userRepo.save(user);
	}

}
