package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

	List<User> list = new ArrayList<>();

	public List<User> getAllUser() {
		return this.list;
	}

	public User getByName(String name) {
		return this.list.stream().filter(user -> user.getUsername().equalsIgnoreCase(name)).findAny().orElse(null);
	}

	public User addUser(User user) {
		this.list.add(user);

		return user;
	}

}
