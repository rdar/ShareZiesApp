package com.webApp.dao;

import java.util.List;

import com.webApp.model.User;


public interface UserDao {

	User findById(long id);
	
	User findByEmail(String email);
	
	void save(User user);
	
	void deleteByEmail(String email);
	
	List<User> findAllUsers();
	
	List<User> findUsersByName(String name);

	
}

