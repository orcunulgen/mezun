package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.EducationInfoDAO;
import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;

@Service
public class EducationInfoService {
	
	@Autowired
	private EducationInfoDAO educationInfoDAO;


	
	public EducationInfoDAO getEducationInfoDAO() {
		return educationInfoDAO;
	}

	public void setEducationInfoDAO(EducationInfoDAO educationInfoDAO) {
		this.educationInfoDAO = educationInfoDAO;
	}

	public void updateEducationInfo(EducationInfo educationInfo) {
		getEducationInfoDAO().updateEducationInfo(educationInfo);
	}

	public void addEducationInfo(EducationInfo educationInfo) {
		getEducationInfoDAO().addEducationInfo(educationInfo);
	}

	public List<University> allUniversities() {
		return getEducationInfoDAO().allUniversities();
	}

	public List<Faculty> allFaculties(University university) {
		return getEducationInfoDAO().allFaculties(university);
	}
	
	public List<Department> allDepartments(Faculty faculty) {
		return getEducationInfoDAO().allDepartments(faculty);
	}

	public List<EducationLevel> allEducationLevels() {
		return getEducationInfoDAO().allEducationLevels();
	}

	public List<EducationInfo> allEducations(User loggedUser) {
		return getEducationInfoDAO().allEducations(loggedUser);
	}
	
	public void deleteEducationInfo(EducationInfo selectedEducationInfo) {
		getEducationInfoDAO().deleteEducationInfo(selectedEducationInfo);
		
	}
}