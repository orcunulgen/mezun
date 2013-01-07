package com.orcun.mezun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.UserDAO;
import com.orcun.mezun.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public User findByUserId(String email) {
		return userDAO.findByUserId(email);
	}


}
