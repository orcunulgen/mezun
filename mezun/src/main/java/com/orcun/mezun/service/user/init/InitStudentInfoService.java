package com.orcun.mezun.service.user.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.init.InitStudentInfoDAO;
import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.User;

@Service
public class InitStudentInfoService {

	@Autowired
	private InitStudentInfoDAO initStudentInfoDAO;

	public InitStudentInfoDAO getInitStudentInfoDAO() {
		return initStudentInfoDAO;
	}

	public void setInitStudentInfoDAO(InitStudentInfoDAO initStudentInfoDAO) {
		this.initStudentInfoDAO = initStudentInfoDAO;
	}

	public boolean areThereContactInfo(User loggedUser) {
		if (getInitStudentInfoDAO().studentContactInfo(loggedUser).size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean areThereParentInfo(User loggedUser) {
		if (getInitStudentInfoDAO().studentParentInfo(loggedUser).size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean areThereHighSchoolInfo(User loggedUser) {
		if (getInitStudentInfoDAO().studentThereHighSchoolInfo(loggedUser)
				.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean areThereUniversity(User loggedUser) {
		if (getInitStudentInfoDAO().studentThereUniversity(loggedUser).size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean IsValidInitStudentInfo(User loggedUser) {

		if (areThereContactInfo(loggedUser)
				&& areThereHighSchoolInfo(loggedUser)
				&& areThereParentInfo(loggedUser)
				&& areThereUniversity(loggedUser)) {
			return true;

		} else {
			return false;
		}
	}

	public boolean saveInitStudentInfo(Contact contact, ParentInfo parentInfo,
			HighSchool highSchool, EducationInfo educationInfo) {
		
		if(getInitStudentInfoDAO().saveInitStudentInfo(contact,parentInfo,highSchool,educationInfo)){
			return true;
		}else{
			return false;
		}
	}
}
