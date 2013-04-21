package com.orcun.mezun.view.admin;

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

import org.primefaces.event.FlowEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.AdminBasicInfo;
import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.admin.InitAdminBasicInfoService;
import com.orcun.mezun.service.user.EducationInfoService;

@ManagedBean
@ViewScoped
public class InitAdminBasicInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	
	private AdminBasicInfo adminBasicInfo;

	// ---------------university_info--------------------------
	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{initAdminBasicInfoService}")
	private InitAdminBasicInfoService initAdminBasicInfoService;

	@PostConstruct
	public void init() {

		universities = educationInfoService.allUniversities();
		adminBasicInfo=new AdminBasicInfo();
	}

	public void addUniversityChangeFaculty() {
		universityAddFaculties = getEducationInfoService().allFaculties(
				getAdminBasicInfo().getUniversity());
		facultyAddDepartments = new ArrayList<Department>();
	}

	public void addFacultyChangeDepartment() {
		facultyAddDepartments = getEducationInfoService().allDepartments(
				getAdminBasicInfo().getFaculty());
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public AdminBasicInfo getAdminBasicInfo() {
		return adminBasicInfo;
	}

	public void setAdminBasicInfo(AdminBasicInfo adminBasicInfo) {
		this.adminBasicInfo = adminBasicInfo;
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

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public InitAdminBasicInfoService getInitAdminBasicInfoService() {
		return initAdminBasicInfoService;
	}

	public void setInitAdminBasicInfoService(
			InitAdminBasicInfoService initAdminBasicInfoService) {
		this.initAdminBasicInfoService = initAdminBasicInfoService;
	}

	public String onFlowProcess(FlowEvent event) {

		return event.getNewStep();

	}

	public void saveInitAdminBasicInfo() throws IOException {

		try {

			getAdminBasicInfo().setUser(getLoggedUser());
			if (getInitAdminBasicInfoService().saveInitAdminBasicInfo(getAdminBasicInfo())) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"index.xhtml");

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Admin konfigürasyon bilgileri kaydedilemedi.",
						"Lütfen daha sonra yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
