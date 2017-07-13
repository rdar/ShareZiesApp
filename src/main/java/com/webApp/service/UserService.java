package com.webApp.service;

import java.util.List;

import com.webApp.model.User;


public interface UserService {
	
	User findById(long id);
	
	User findByEmail(String email);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByEmail(String email);

	List<User> findAllUsers(); 
	
	void giveUser(User user);
	
	void changeSubscrition(User user);
	
	void savepic(User user);
	
	void resetpswd(User user);
	
	List<User> findUsersByName(String name);

	
}