package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JwtHelper;
import com.example.demo.jwt.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper helper;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		String token = this.helper.generateToken(userDetails);
		String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst()
				.orElse("ROLE_USER");
		JwtResponse response = new JwtResponse(token, userDetails.getUsername(), role.split("_")[1].toString());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@PostMapping("/create-role")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User byUsername = repository.findByUsername(user.getUsername());
		if (byUsername == null) {
			User save = repository.save(user);
			return ResponseEntity.ok(save);
		}
		return ResponseEntity.ok(null);
	}
}
