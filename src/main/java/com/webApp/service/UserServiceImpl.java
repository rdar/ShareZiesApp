package com.webApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webApp.dao.UserDao;
import com.webApp.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	public User findById(long id) {
		return dao.findById(id);
	}

	public User findByEmail(String email) {
		User user = dao.findByEmail(email);
		return user;
	}

	public void saveUser(User user) {
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findByEmail(user.getEmailAddress());
		    if(entity!=null){
		    	entity.setAddress(user.getAddress());
		    	
		    	entity.setBackground(user.getBackground());
		    	entity.setCity(user.getCity());
		    	entity.setEmailAddress(user.getEmailAddress());
		    	entity.setFname(user.getFname());
		    	
		    	entity.setTel(user.getTel());
		    	entity.setSubscription(user.getSubscription());
		    	entity.setState(user.getState());
		    	entity.setWebsite(user.getWebsite());
		    	entity.setSelectClub(user.getSelectClub());
		    }
	}

	
	public void deleteUserByEmail(String email) {
		dao.deleteByEmail(email);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	@Override
	public void giveUser(User user) {
		User entity = dao.findById(user.getId());
	    if(entity!=null){
	       entity.setRoles(user.getRoles());
	    }
	}

	@Override
	public void changeSubscrition(User user) {
		User entity = dao.findById(user.getId());
	    if(entity!=null){
	       entity.setSubscription(user.getSubscription());
	    }
	}

	@Override
	public void resetpswd(User user) {
		User entity = dao.findById(user.getId());
	    if(entity!=null){
	       entity.setPassword(user.getPassword());
	    }
	}

	@Override
	public List<User> findUsersByName(String name) {
		return dao.findUsersByName(name);
	}

	@Override
	public void savepic(User user) {
		User entity = dao.findById(user.getId());
	    if(entity!=null){
	       entity.setPicture(user.getPicture());
	    }
	}

	
	
	
}
