package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.AreaOfInterestDAO;
import com.orcun.mezun.model.AreaOfInterest;
import com.orcun.mezun.model.User;

@Service
public class AreaOfInterestService {
	
	@Autowired
	private AreaOfInterestDAO areaOfInterestDAO;

	public AreaOfInterestDAO getAreaOfInterestDAO() {
		return areaOfInterestDAO;
	}

	public void setAreaOfInterestDAO(AreaOfInterestDAO areaOfInterestDAO) {
		this.areaOfInterestDAO = areaOfInterestDAO;
	}

	public void updateAreaOfInterest(AreaOfInterest areaOfInterest) {
		getAreaOfInterestDAO().updateAreaOfInterest(areaOfInterest);
	}

	public void addAreaOfInterest(AreaOfInterest areaOfInterest) {
		getAreaOfInterestDAO().addAreaOfInterest(areaOfInterest);
	}

	public List<AreaOfInterest> allAreaOfInterest(User loggedUser) {
		return getAreaOfInterestDAO().allAreaOfInterest(loggedUser);
	}

	public void deleteAreaOfInterest(AreaOfInterest selectedAreaOfInterest) {
		getAreaOfInterestDAO().deleteAreaOfInterest(selectedAreaOfInterest);
		
	}

}
