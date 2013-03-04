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
import org.springframework.security.core.context.SecurityContextHolder;

import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.service.user.EducationInfoService;

@ManagedBean
@ViewScoped
public class EducationInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private EducationInfo educationInfo;

	private Boolean isStudent = true;

	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();
	private List<Faculty> universityUpdateFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();
	private List<Department> facultyUpdateDepartments = new ArrayList<Department>();

	private List<EducationLevel> educationLevels = new ArrayList<EducationLevel>();

	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	private List<EducationInfo> educations = new ArrayList<EducationInfo>();
	private EducationInfo selectedEducationInfo;
	private Boolean selectedEduInfoIsStudent;

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{signUpService}")
	private SignUpService signUpService;

	@PostConstruct
	public void init() {
		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();
		educations = educationInfoService.allEducations(getLoggedUser());
		gradingSystems = educationInfoService.allGradingSystems();
		if (educationInfo == null) {
			educationInfo = new EducationInfo();

		}
	}

	public void initSelectedEducationInfo(EducationInfo selectedEducationInfo) {
		this.selectedEducationInfo = selectedEducationInfo;
		if (getSelectedEducationInfo().getEndDate() == null) {
			this.selectedEduInfoIsStudent = true;
		} else {
			this.selectedEduInfoIsStudent = false;
		}
		updateUniversityChangeFaculty();
		updateFacultyChangeDepartment();
	}

	public void deleteSelectedEducationInfo(EducationInfo selectedEducationInfo)
			throws IOException {
		initSelectedEducationInfo(selectedEducationInfo);
		try {

			if (getLoggedUser().getRoles().get(0).getRole()
					.equals("ROLE_ALUMNI")
					&& selectedUniversityIsLast(selectedEducationInfo)) {
				changeRoleToStudent();
				getEducationInfoService().deleteEducationInfo(
						getSelectedEducationInfo());
				SecurityContextHolder.clearContext();
			} else {

				getEducationInfoService().deleteEducationInfo(
						getSelectedEducationInfo());
			}

			FacesContext
					.getCurrentInstance()
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

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
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

	public void setUniversityUpdateFaculties(
			List<Faculty> universityUpdateFaculties) {
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

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	public Boolean getSelectedEduInfoIsStudent() {
		return selectedEduInfoIsStudent;
	}

	public void setSelectedEduInfoIsStudent(Boolean selectedEduInfoIsStudent) {
		this.selectedEduInfoIsStudent = selectedEduInfoIsStudent;
	}

	public void addUniversityChangeFaculty() {
		universityAddFaculties = getEducationInfoService().allFaculties(
				getEducationInfo().getUniversity());
		facultyAddDepartments = new ArrayList<Department>();
	}

	public void updateUniversityChangeFaculty() {
		universityUpdateFaculties = getEducationInfoService().allFaculties(
				getSelectedEducationInfo().getUniversity());
		facultyUpdateDepartments = new ArrayList<Department>();
	}

	public void addFacultyChangeDepartment() {
		facultyAddDepartments = getEducationInfoService().allDepartments(
				getEducationInfo().getFaculty());
	}

	public void updateFacultyChangeDepartment() {
		facultyUpdateDepartments = getEducationInfoService().allDepartments(
				getSelectedEducationInfo().getFaculty());
	}

	public void checkURL() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String userParameter = request.getParameter("user");

		if (userParameter == null || userParameter.equals("")) {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		} else if (!userParameter.equals(getLoggedUser().getTcno().toString())) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"personal_info.xhtml?user="
									+ getLoggedUser().getTcno());

		}
	}

	public void changeRoleToAlumni() {

		Role roleAlumni = new Role();
		List<Role> roleList = new ArrayList<Role>();
		roleAlumni = signUpService.getRoleInfo("ROLE_ALUMNI");
		roleList.add(roleAlumni);

		getLoggedUser().setRoles(roleList);

		getEducationInfoService().updateUser(getLoggedUser());
	}

	public void changeRoleToStudent() {

		Role roleStudent = new Role();
		List<Role> roleList = new ArrayList<Role>();
		roleStudent = signUpService.getRoleInfo("ROLE_STUDENT");
		roleList.add(roleStudent);

		getLoggedUser().setRoles(roleList);

		getEducationInfoService().updateUser(getLoggedUser());
	}

	public boolean selectedUniversityIsLast(EducationInfo selectedEduInfo) {
		List<EducationInfo> graduatedUniversities = getEducationInfoService()
				.findGraduatedUniversities(getLoggedUser());
		if (graduatedUniversities.size() == 1
				&& graduatedUniversities.get(0).equals(selectedEduInfo)) {
			return true;
		} else {
			return false;
		}
	}

	public void addEducationInfo() throws IOException {
		try {

			if (getIsStudent()) {
				getEducationInfo().setEndDate(null);

				Date registeredDate = new Date();

				int differenceStartRegister = Days.daysBetween(
						new DateTime(getEducationInfo().getStartDate()),
						new DateTime(registeredDate)).getDays();
				if (differenceStartRegister > 0) {

					getEducationInfo().setUser(getLoggedUser());
					getEducationInfoService().addEducationInfo(
							getEducationInfo());

					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"university.xhtml?user="
											+ getLoggedUser().getTcno());

				} else {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Başlangıç tarihi geçmiş zamana ait olmalıdır.",
							"Lütfen yeniden deneyiniz.");
					FacesContext.getCurrentInstance().addMessage(null, fm);

				}
			} else {
				int differenceStartEnd = Days.daysBetween(
						new DateTime(getEducationInfo().getStartDate()),
						new DateTime(getEducationInfo().getEndDate()))
						.getDays();

				Date registeredDate = new Date();

				int differenceStartRegister = Days.daysBetween(
						new DateTime(getEducationInfo().getStartDate()),
						new DateTime(registeredDate)).getDays();

				if (differenceStartEnd > 0 && differenceStartRegister > 0) {

					getEducationInfo().setUser(getLoggedUser());
					getEducationInfoService().addEducationInfo(
							getEducationInfo());

					if (getLoggedUser().getRoles().get(0).getRole()
							.equals("ROLE_STUDENT")) {
						
						changeRoleToAlumni();
						SecurityContextHolder.clearContext();
					}
					

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

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

	public void updateEducationInfo() throws IOException {
		try {

			if (getSelectedEduInfoIsStudent()) {
				getSelectedEducationInfo().setEndDate(null);
				Date registeredDate = new Date();

				int differenceStartRegister = Days
						.daysBetween(
								new DateTime(getSelectedEducationInfo()
										.getStartDate()),
								new DateTime(registeredDate)).getDays();

				if (differenceStartRegister > 0) {

					
					if (getLoggedUser().getRoles().get(0).getRole()
							.equals("ROLE_ALUMNI")
							&& selectedUniversityIsLast(getSelectedEducationInfo())) {
						changeRoleToStudent();
						SecurityContextHolder.clearContext();
					}
					getEducationInfoService().updateEducationInfo(
							getSelectedEducationInfo());

					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"university.xhtml?user="
											+ getLoggedUser().getTcno());
				} else {
					FacesMessage fm = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Başlangıç tarihi geçmiş zamana ait olmalıdır.",
							"Lütfen yeniden deneyiniz.");
					FacesContext.getCurrentInstance().addMessage(null, fm);
				}
			} else {
				int differenceStartEnd = Days
						.daysBetween(
								new DateTime(getSelectedEducationInfo()
										.getStartDate()),
								new DateTime(getSelectedEducationInfo()
										.getEndDate())).getDays();

				Date registeredDate = new Date();

				int differenceStartRegister = Days
						.daysBetween(
								new DateTime(getSelectedEducationInfo()
										.getStartDate()),
								new DateTime(registeredDate)).getDays();

				if (differenceStartEnd > 0 && differenceStartRegister > 0) {

					if (getLoggedUser().getRoles().get(0).getRole()
							.equals("ROLE_STUDENT")) {
						
						changeRoleToAlumni();
						SecurityContextHolder.clearContext();
					}
					
					getEducationInfoService().updateEducationInfo(
							getSelectedEducationInfo());

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
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
