package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Days;
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

@ManagedBean
@ViewScoped
public class EducationInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;
	
	private EducationInfo educationInfo;
	
	private List<University> universities=new ArrayList<University>();
	
	private List<Faculty> universityAddFaculties=new ArrayList<Faculty>();
	private List<Faculty> universityUpdateFaculties=new ArrayList<Faculty>();
	
	private List<Department> facultyAddDepartments=new ArrayList<Department>();
	private List<Department> facultyUpdateDepartments=new ArrayList<Department>();
	
	private List<EducationLevel> educationLevels=new ArrayList<EducationLevel>();
	
	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	
	private List<EducationInfo> educations = new ArrayList<EducationInfo>();
	private EducationInfo selectedEducationInfo;
	
	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;
	
	@PostConstruct
	public void init() {
		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();
		educations=educationInfoService.allEducations(getLoggedUser());
		gradingSystems = educationInfoService.allGradingSystems();
		if (educationInfo == null) {
			educationInfo = new EducationInfo();

		}
	}
	
	public void initSelectedEducationInfo(EducationInfo selectedEducationInfo){
		this.selectedEducationInfo=selectedEducationInfo;
		updateUniversityChangeFaculty();
		updateFacultyChangeDepartment();
	}
	
	public void deleteSelectedEducationInfo(EducationInfo selectedEducationInfo) throws IOException {
		initSelectedEducationInfo(selectedEducationInfo);
		try {
			getEducationInfoService().deleteEducationInfo(
					getSelectedEducationInfo());
			
			FacesContext.getCurrentInstance()
			.getExternalContext()
			.redirect(
					"university.xhtml?user="
							+ getLoggedUser().getTcno());

		} catch (DataAccessException e) {
			e.printStackTrace();
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

	public List<Faculty> getUniversityUpdateFaculties() {
		return universityUpdateFaculties;
	}

	public void setUniversityUpdateFaculties(List<Faculty> universityUpdateFaculties) {
		this.universityUpdateFaculties = universityUpdateFaculties;
	}

	public List<Department> getFacultyAddDepartments() {
		return facultyAddDepartments;
	}

	public void setFacultyAddDepartments(List<Department> facultyAddDepartments) {
		this.facultyAddDepartments = facultyAddDepartments;
	}

	public List<Department> getFacultyUpdateDepartments() {
		return facultyUpdateDepartments;
	}

	public void setFacultyUpdateDepartments(
			List<Department> facultyUpdateDepartments) {
		this.facultyUpdateDepartments = facultyUpdateDepartments;
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

	public List<EducationInfo> getEducations() {
		return educations;
	}

	public void setEducations(List<EducationInfo> educations) {
		this.educations = educations;
	}

	public EducationInfo getSelectedEducationInfo() {
		return selectedEducationInfo;
	}

	public void setSelectedEducationInfo(EducationInfo selectedEducationInfo) {
		this.selectedEducationInfo = selectedEducationInfo;
	}

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public void addUniversityChangeFaculty(){
		universityAddFaculties=getEducationInfoService().allFaculties(getEducationInfo().getUniversity());
		facultyAddDepartments=new ArrayList<Department>();
	}
	public void updateUniversityChangeFaculty(){
		universityUpdateFaculties=getEducationInfoService().allFaculties(getSelectedEducationInfo().getUniversity());
		facultyUpdateDepartments=new ArrayList<Department>();
	}
	
	public void addFacultyChangeDepartment(){
		facultyAddDepartments=getEducationInfoService().allDepartments(getEducationInfo().getFaculty());
	}
	public void updateFacultyChangeDepartment(){
		facultyUpdateDepartments=getEducationInfoService().allDepartments(getSelectedEducationInfo().getFaculty());
	}
	
	public void checkURL() throws IOException{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String userParameter=request.getParameter("user");
		
		if(userParameter==null || userParameter.equals("")){
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}else if(!userParameter.equals(getLoggedUser().getTcno().toString())){
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("personal_info.xhtml?user="+getLoggedUser().getTcno());
			
		}
	}
	
	public void addEducationInfo() throws IOException {
		try {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getEducationInfo().getStartDate()),
					new DateTime(getEducationInfo().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getEducationInfo().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister > 0) {

				getEducationInfo().setUser(getLoggedUser());
				getEducationInfoService().addEducationInfo(getEducationInfo());

				/*
				 * FacesMessage fm = new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "Kaydınız tamamlandı.", "");
				 * 
				 * FacesContext.getCurrentInstance().addMessage(null, fm);
				 */

				/*
				 * FacesContext.getCurrentInstance().getExternalContext()
				 * .getFlash().setKeepMessages(true);
				 */
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"university.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}
	
	public void updateEducationInfo() throws IOException {
		try {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getSelectedEducationInfo().getStartDate()),
					new DateTime(getSelectedEducationInfo().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedEducationInfo().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister > 0) {

				getEducationInfoService().updateEducationInfo(getSelectedEducationInfo());

				/*
				 * FacesMessage fm = new
				 * FacesMessage(FacesMessage.SEVERITY_ERROR,
				 * "Kaydınız tamamlandı.", "");
				 * 
				 * FacesContext.getCurrentInstance().addMessage(null, fm);
				 */

				/*
				 * FacesContext.getCurrentInstance().getExternalContext()
				 * .getFlash().setKeepMessages(true);
				 */
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"university.xhtml?user="
										+ getLoggedUser().getTcno());

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}


}
