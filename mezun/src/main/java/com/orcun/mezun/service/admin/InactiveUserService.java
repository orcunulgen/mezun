package com.orcun.mezun.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.admin.InactiveUserDAO;
import com.orcun.mezun.model.User;

@Service
public class InactiveUserService {
	
	@Autowired
	private InactiveUserDAO inactiveUserDAO;

	public InactiveUserDAO getInactiveUserDAO() {
		return inactiveUserDAO;
	}

	public void setInactiveUserDAO(InactiveUserDAO inactiveUserDAO) {
		this.inactiveUserDAO = inactiveUserDAO;
	}
	
	public List<User> inactiveUserList(Date now){
		return getInactiveUserDAO().inactiveUserList(now);
	}
	
	public User findUserByTcno(User user){
		return getInactiveUserDAO().findUserByTcno(user);
	}

	public void sendAdminForActivation(User user) {
		getInactiveUserDAO().sendAdminForActivation(user);
	}

	public List<User> getInactiveUserList() {
		return getInactiveUserDAO().getInactiveUserList();
	}

}
