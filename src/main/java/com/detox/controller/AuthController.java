package com.detox.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detox.model.User;
import com.detox.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/signup")
	public User signup(@RequestBody Map<String , String> data) {
		User user = new User();
		user.setUsername(data.get("username"));
		user.setPassword(data.get("password"));
		user.setPhone(data.get("phone"));
		user.setPoints(0);
		
		return userRepo.save(user);
		
	}
	@PostMapping("/login")
	public User login(@RequestBody Map<String, String> data) {
		String username = data.get("username");
		String password = data.get("password");
		
		User user = userRepo.findbyUser(username);
		
		if (user!= null  && user.getPassword().equals(password)){
			return user;
			
		}
		throw new RuntimeException("Invalid username  and password");
		
		
	}
}
