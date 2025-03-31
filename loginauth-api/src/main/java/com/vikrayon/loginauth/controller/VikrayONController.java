package com.vikrayon.loginauth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikrayon.loginauth.model.VikrayONUsers;
import com.vikrayon.loginauth.repo.VikrayONRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class VikrayONController {
	@Autowired
	VikrayONRepo VRepo;
	
	// User Registration
    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody VikrayONUsers user) {
        // Check if email already exists
        Optional<VikrayONUsers> existingUser = VRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered!");
        }
        
        System.out.println("Received password: " + user.getPassword());
        System.out.println("Received confirmpass: " + user.getConfirmpass());

        // Check if passwords match
        if (!user.getPassword().equals(user.getConfirmpass())) {
            return ResponseEntity.badRequest().body("Passwords do not match!");
        }
        String uname = user.getEmail();
        String finaluname = uname.substring(0, uname.indexOf('@'));
        System.out.println(finaluname);
        user.setUsername(finaluname);
        VRepo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // User Login
    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody VikrayONUsers user) {
        Optional<VikrayONUsers> existingUser = VRepo.findByEmail(user.getEmail());

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password!");
        }
    }
}
