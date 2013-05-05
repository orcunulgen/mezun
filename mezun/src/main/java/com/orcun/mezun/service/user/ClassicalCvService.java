package com.orcun.mezun.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.ClassicalCvDAO;
import com.orcun.mezun.model.ClassicalCV;
import com.orcun.mezun.model.User;

@Service
public class ClassicalCvService {
	
	@Autowired
	private ClassicalCvDAO classicalCvDAO;

	

	public ClassicalCvDAO getClassicalCvDAO() {
		return classicalCvDAO;
	}

	public void setClassicalCvDAO(ClassicalCvDAO classicalCvDAO) {
		this.classicalCvDAO = classicalCvDAO;
	}

	public ClassicalCV findClassicalCvByUser(User user) {
		if (user != null) {
			return getClassicalCvDAO().findClassicalCvByUser(user);
		} else {
			return null;
		}

	}

	public void updateClassicalCv(ClassicalCV classicalCV) {
		getClassicalCvDAO().updateClassicalCv(classicalCV);
	}

	public void addClassicalCv(ClassicalCV classicalCV) {
		getClassicalCvDAO().addClassicalCv(classicalCV);
	}

}
