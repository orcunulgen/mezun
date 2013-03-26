package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.InitAlumniInfoDAO;
import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.User;

@Service
public class InitAlumniInfoService {

	@Autowired
	private InitAlumniInfoDAO initAlumniInfoDAO;


	public InitAlumniInfoDAO getInitAlumniInfoDAO() {
		return initAlumniInfoDAO;
	}

	public void setInitAlumniInfoDAO(InitAlumniInfoDAO initAlumniInfoDAO) {
		this.initAlumniInfoDAO = initAlumniInfoDAO;
	}

	public boolean areThereContactInfo(User loggedUser) {
		if (getInitAlumniInfoDAO().studentContactInfo(loggedUser).size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean areThereUniversity(User loggedUser) {
		if (getInitAlumniInfoDAO().studentThereUniversity(loggedUser).size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean IsValidInitAlumniInfo(User loggedUser) {

		if (areThereContactInfo(loggedUser)
				&& areThereUniversity(loggedUser)) {
			return true;

		} else {
			return false;
		}
	}

	public boolean saveInitAlumniInfo(Contact contact,EducationInfo educationInfo) {
		
		if(getInitAlumniInfoDAO().saveInitAlumniInfo(contact,educationInfo)){
			return true;
		}else{
			return false;
		}
	}
}
