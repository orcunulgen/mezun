package com.orcun.mezun.view.user;

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
import javax.faces.context.Flash;

import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContext;

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

	private List<Integer> classInfos = new ArrayList<Integer>();

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
		if (getSelectedEducationInfo().getEndYear() == null) {
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

		/*
		 * if (getLoggedUser().getRoles().get(0).getRole()
		 * .equals("ROLE_ALUMNI") &&
		 * selectedUniversityIsLast(selectedEducationInfo)) {
		 * changeRoleToStudent(); getEducationInfoService().deleteEducationInfo(
		 * getSelectedEducationInfo()); SecurityContextHolder.clearContext(); }
		 * else {
		 */

		getEducationInfoService().deleteEducationInfo(
				getSelectedEducationInfo());
		// }

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("university.xhtml?user=" + getLoggedUser().getTcno());

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

	public String addEducationInfo() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getIsStudent()) {
			getEducationInfo().setEndYear(null);

			DateTime registeredDate = new DateTime();

			int differenceStartRegister = registeredDate.getYear()
					- getEducationInfo().getStartYear();
			if (differenceStartRegister > 0) {

				getEducationInfo().setUser(getLoggedUser());
				getEducationInfoService().addEducationInfo(getEducationInfo());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		} else {
			int differenceStartEnd = getEducationInfo().getEndYear()
					- getEducationInfo().getStartYear();

			DateTime registeredDate = new DateTime();

			int differenceStartRegister = registeredDate.getYear()
					- getEducationInfo().getStartYear();

			int differenceEndRegister = registeredDate.getYear()
					- getEducationInfo().getEndYear();

			if (differenceStartEnd > 0 && differenceStartRegister > 0
					&& differenceEndRegister >= 0) {

				getEducationInfo().setUser(getLoggedUser());
				getEducationInfoService().addEducationInfo(getEducationInfo());

				/*
				 * if (getLoggedUser().getRoles().get(0).getRole()
				 * .equals("ROLE_STUDENT")) {
				 * 
				 * changeRoleToAlumni(); SecurityContextHolder.clearContext(); }
				 */

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç ve bitiş tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		}

		return "university.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();
	}

	public String updateEducationInfo() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getSelectedEduInfoIsStudent()) {
			getSelectedEducationInfo().setEndYear(null);

			DateTime registeredDate = new DateTime();

			int differenceStartRegister = registeredDate.getYear()
					- getSelectedEducationInfo().getStartYear();
			if (differenceStartRegister > 0) {

				/*
				 * if (getLoggedUser().getRoles().get(0).getRole()
				 * .equals("ROLE_ALUMNI") &&
				 * selectedUniversityIsLast(getSelectedEducationInfo())) {
				 * changeRoleToStudent(); SecurityContextHolder.clearContext();
				 * }
				 */
				getEducationInfoService().updateEducationInfo(
						getSelectedEducationInfo());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);
			}
		} else {
			int differenceStartEnd = getSelectedEducationInfo().getEndYear()
					- getSelectedEducationInfo().getStartYear();

			DateTime registeredDate = new DateTime();

			int differenceStartRegister = registeredDate.getYear()
					- getSelectedEducationInfo().getStartYear();

			int differenceEndRegister = registeredDate.getYear()
					- getSelectedEducationInfo().getEndYear();

			if (differenceStartEnd > 0 && differenceStartRegister > 0
					&& differenceEndRegister >= 0) {

				/*
				 * if (getLoggedUser().getRoles().get(0).getRole()
				 * .equals("ROLE_STUDENT")) {
				 * 
				 * changeRoleToAlumni(); SecurityContextHolder.clearContext(); }
				 */

				getEducationInfoService().updateEducationInfo(
						getSelectedEducationInfo());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç ve bitiş tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}
		}

		return "university.xhtml?faces-redirect=true&user="
				+ getLoggedUser().getTcno();

	}

}
