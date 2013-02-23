package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ParentInfoDAO;
import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;

@Service
public class ParentInfoService {
	
	@Autowired
	private ParentInfoDAO parentInfoDAO;

	
	public ParentInfoDAO getParentInfoDAO() {
		return parentInfoDAO;
	}

	public void setParentInfoDAO(ParentInfoDAO parentInfoDAO) {
		this.parentInfoDAO = parentInfoDAO;
	}
	
	public void addParentInfo(ParentInfo parentInfo) {
		getParentInfoDAO().addParentInfo(parentInfo);
	}
	
	public void updateParentInfo(ParentInfo parentInfo) {
		getParentInfoDAO().updateParentInfo(parentInfo);
	}

	public ParentInfo findParentInfoByUser(User loggedUser) {
		if (loggedUser != null) {
			return parentInfoDAO.findParentInfoByUser(loggedUser);
		} else {
			return null;
		}
	}

}
