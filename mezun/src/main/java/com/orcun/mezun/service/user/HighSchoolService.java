package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.HighSchoolDAO;
import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.HighSchoolType;
import com.orcun.mezun.model.User;

@Service
public class HighSchoolService {
	
	@Autowired
	private HighSchoolDAO highSchoolDAO;


	public HighSchoolDAO getHighSchoolDAO() {
		return highSchoolDAO;
	}

	public void setHighSchoolDAO(HighSchoolDAO highSchoolDAO) {
		this.highSchoolDAO = highSchoolDAO;
	}

	public void updateHighSchool(HighSchool highSchool) {
		getHighSchoolDAO().updateHighSchool(highSchool);
	}

	public void addHighSchool(HighSchool highSchool) {
		getHighSchoolDAO().addHighSchool(highSchool);
	}

	public List<HighSchoolType> allHighSchoolTypes() {
		return getHighSchoolDAO().allHighSchoolTypes();
	}

	public List<GradingSystem> allGradingSystems() {
		return getHighSchoolDAO().allGradingSystems();
	}

	public HighSchool findHighSchoolByUser(User user) {
		if (user != null) {
			return highSchoolDAO.findHighSchoolByUser(user);
		} else {
			return null;
		}

	}

	
}
