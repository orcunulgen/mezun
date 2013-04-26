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
import com.orcun.mezun.service.user.init.InitAlumniInfoService;

@ManagedBean
@ViewScoped
public class AlumniUniversityView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private EducationInfo educationInfo;

	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();

	private List<EducationLevel> educationLevels = new ArrayList<EducationLevel>();

	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{initAlumniInfoService}")
	private InitAlumniInfoService initAlumniInfoService;

	@PostConstruct
	public void init() {

		gradingSystems = educationInfoService.allGradingSystems();

		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();

		if (educationInfo == null) {
			educationInfo = new EducationInfo();
			educationInfo.setUniversity(getEducationInfoService().getYTU(
					(long) 1));
			educationInfo.setFaculty(getEducationInfoService().getYTUFaculty(
					(long) 2));
			educationInfo.setDepartment(getEducationInfoService().getYTUCE(
					(long) 4));

			addUniversityChangeFaculty();
			addFacultyChangeDepartment();
		}
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

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public InitAlumniInfoService getInitAlumniInfoService() {
		return initAlumniInfoService;
	}

	public void setInitAlumniInfoService(
			InitAlumniInfoService initAlumniInfoService) {
		this.initAlumniInfoService = initAlumniInfoService;
	}

	public void saveAlumniUniversity() throws IOException {
		try {

			DateTime registeredDate = new DateTime();

			int differenceStartEnd = getEducationInfo().getEndYear()
					- getEducationInfo().getStartYear();

			int differenceStartRegister = registeredDate.getYear()
					- getEducationInfo().getStartYear();
			
			int differenceEndRegister = registeredDate.getYear()
					- getEducationInfo().getEndYear();

			if (differenceStartEnd > 0 && differenceStartRegister > 0
					&& differenceEndRegister >= 0) {

				getEducationInfo().setUser(getLoggedUser());
				getEducationInfoService().addEducationInfo(getEducationInfo());

				if (!getInitAlumniInfoService().IsValidInitAlumniInfo(
						getLoggedUser())) {
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"init_alumni_info.xhtml?user="
											+ getLoggedUser().getTcno());
				} else {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../index.xhtml");
				}

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Üniversite başlangıç ve bitiş tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
