package com.orcun.mezun.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.PersonalInfoDAO;
import com.orcun.mezun.model.User;

@Service
public class PersonalInfoService {

	@Autowired
	private PersonalInfoDAO personalInfoDAO;

	public PersonalInfoDAO getPersonalInfoDAO() {
		return personalInfoDAO;
	}

	public void setPersonalInfoDAO(PersonalInfoDAO personalInfoDAO) {
		this.personalInfoDAO = personalInfoDAO;
	}

	public void updatePersonalInfo(User personalInfo) {
		getPersonalInfoDAO().updatePersonalInfo(personalInfo);
	}
}
