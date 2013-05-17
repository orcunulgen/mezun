package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AllPostDAO;

@Service
public class AllPostService {
	
	@Autowired
	private AllPostDAO allPostDAO;

	public AllPostDAO getAllPostDAO() {
		return allPostDAO;
	}

	public void setAllPostDAO(AllPostDAO allPostDAO) {
		this.allPostDAO = allPostDAO;
	}

}
