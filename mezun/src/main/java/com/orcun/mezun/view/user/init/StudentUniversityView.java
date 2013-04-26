package com.orcun.mezun.view.user.init;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.EducationInfoService;
import com.orcun.mezun.service.user.init.InitStudentInfoService;

@ManagedBean
@ViewScoped
public class StudentUniversityView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private EducationInfo educationInfo;

	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();

	private List<EducationLevel> educationLevels = new ArrayList<EducationLevel>();

	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	private List<Integer> classInfos = new ArrayList<Integer>();

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{initStudentInfoService}")
	private InitStudentInfoService initStudentInfoService;

	@PostConstruct
	public void init() {
		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();
		gradingSystems = educationInfoService.allGradingSystems();
		if (educationInfo == null) {
			educationInfo = new EducationInfo();

		}
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public EducationInfo getEducationInfo() {
		return educationInfo;
	}

	public void setEducationInfo(EducationInfo educationInfo) {
		this.educationInfo = educationInfo;
	}

	public List<University> getUniversities() {
		return universities;
	}

	public void setUniversities(List<University> universities) {
		this.universities = universities;
	}

	public List<Faculty> getUniversityAddFaculties() {
		return universityAddFaculties;
	}

	public void setUniversityAddFaculties(List<Faculty> universityAddFaculties) {
		this.universityAddFaculties = universityAddFaculties;
	}

	public List<Department> getFacultyAddDepartments() {
		return facultyAddDepartments;
	}

	public void setFacultyAddDepartments(List<Department> facultyAddDepartments) {
		this.facultyAddDepartments = facultyAddDepartments;
	}

	public List<EducationLevel> getEducationLevels() {
		return educationLevels;
	}

	public void setEducationLevels(List<EducationLevel> educationLevels) {
		this.educationLevels = educationLevels;
	}

	public List<GradingSystem> getGradingSystems() {
		return gradingSystems;
	}

	public void setGradingSystems(List<GradingSystem> gradingSystems) {
		this.gradingSystems = gradingSystems;
	}

	public List<Integer> getClassInfos() {
		if (this.classInfos.size() == 0) {
			for (int i = 1; i < 7; i++) {
				this.classInfos.add(i);
			}
		}
		return classInfos;
	}

	public void setClassInfos(List<Integer> classInfos) {
		this.classInfos = classInfos;
	}

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public void addUniversityChangeFaculty() {
		universityAddFaculties = getEducationInfoService().allFaculties(
				getEducationInfo().getUniversity());
		facultyAddDepartments = new ArrayList<Department>();
	}

	public void addFacultyChangeDepartment() {
		facultyAddDepartments = getEducationInfoService().allDepartments(
				getEducationInfo().getFaculty());
	}

	public void saveStudentUniversity() throws IOException {
		try {

			getEducationInfo().setEndYear(null);

			DateTime registeredDate = new DateTime();

			int differenceStartRegister = registeredDate.getYear()
					- getEducationInfo().getStartYear();
			if (differenceStartRegister > 0) {

				getEducationInfo().setUser(getLoggedUser());
				getEducationInfoService().addEducationInfo(getEducationInfo());

				if (!getInitStudentInfoService().IsValidInitStudentInfo(
						getLoggedUser())) {
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"init_student_info.xhtml?user="
											+ getLoggedUser().getTcno());
				} else {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../index.xhtml");
				}
			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
