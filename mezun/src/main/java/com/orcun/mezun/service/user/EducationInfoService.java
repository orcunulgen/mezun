package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.EducationInfoDAO;
import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationFeedback;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.GradingSystem;
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

	public List<GradingSystem> allGradingSystems() {
		return getEducationInfoDAO().allGradingSystems();
	}

	public List<EducationInfo> allEducations(User loggedUser) {
		return getEducationInfoDAO().allEducations(loggedUser);
	}

	public void deleteEducationInfo(EducationInfo selectedEducationInfo) {
		getEducationInfoDAO().deleteEducationInfo(selectedEducationInfo);

	}

	public void updateUser(User loggedUser) {
		getEducationInfoDAO().updateUser(loggedUser);
	}

	public List<EducationInfo> findGraduatedUniversities(User loggedUser) {
		return getEducationInfoDAO().findGraduatedUniversities(loggedUser);
	}

	public University getYTU(Long l) {
		return getEducationInfoDAO().getYTU(l);
	}

	public Faculty getYTUFaculty(long l) {
		return getEducationInfoDAO().getYTUFaculty(l);
	}

	public Department getYTUCE(long l) {
		return getEducationInfoDAO().getYTUCE(l);
	}

	public EducationFeedback findFeedbackInfoByEduInfo(
			EducationInfo selectedEducationInfo) {
		if (selectedEducationInfo != null) {
			return getEducationInfoDAO().findFeedbackInfoByEduInfo(
					selectedEducationInfo);
		} else {
			return null;
		}
	}

	public void addFeedbackInfo(EducationFeedback educationFeedback) {
		getEducationInfoDAO().addFeedbackInfo(educationFeedback);

	}

	public void updateFeedbackInfo(EducationFeedback educationFeedback) {
		getEducationInfoDAO().updateFeedbackInfo(educationFeedback);
	}
}
