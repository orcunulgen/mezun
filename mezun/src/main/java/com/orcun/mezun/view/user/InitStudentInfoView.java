package com.orcun.mezun.view.user;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.event.FlowEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.Contact;
import com.orcun.mezun.model.Department;
import com.orcun.mezun.model.EducationInfo;
import com.orcun.mezun.model.EducationLevel;
import com.orcun.mezun.model.Faculty;
import com.orcun.mezun.model.GradingSystem;
import com.orcun.mezun.model.HighSchool;
import com.orcun.mezun.model.HighSchoolType;
import com.orcun.mezun.model.ParentInfo;
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ContactInfoService;
import com.orcun.mezun.service.user.EducationInfoService;
import com.orcun.mezun.service.user.HighSchoolService;
import com.orcun.mezun.service.user.InitStudentInfoService;
import com.orcun.mezun.service.user.ParentInfoService;

@ManagedBean
@ViewScoped
public class InitStudentInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	// -------------------contact_info-----------------------
	private Contact contact;

	// ------------------------------------------------------

	// --------------------parent_info------------------------
	private ParentInfo parentInfo;

	private List<Integer> birthdayYears = new ArrayList<Integer>();

	// -------------------------------------------------------

	// -------------high_school_info---------------------------
	private HighSchool highSchool;

	private List<HighSchoolType> highSchoolTypes = new ArrayList<HighSchoolType>();
	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	// --------------------------------------------------------

	// ---------------university_info--------------------------
	private EducationInfo educationInfo;

	private Boolean isStudent = true;

	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();

	private List<EducationLevel> educationLevels = new ArrayList<EducationLevel>();

	// --------------------------------------------------------

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{highSchoolService}")
	private HighSchoolService highSchoolService;

	@ManagedProperty(value = "#{parentInfoService}")
	private ParentInfoService parentInfoService;

	@ManagedProperty(value = "#{contactInfoService}")
	private ContactInfoService contactInfoService;

	@ManagedProperty(value = "#{initStudentInfoService}")
	private InitStudentInfoService initStudentInfoService;

	@PostConstruct
	public void init() {
		if (contact == null) {
			this.contact = new Contact();
		}

		if (parentInfo == null) {
			parentInfo = new ParentInfo();

		}

		if (highSchool == null) {
			highSchool = new HighSchool();

		}

		highSchoolTypes = highSchoolService.allHighSchoolTypes();
		gradingSystems = highSchoolService.allGradingSystems();

		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();

		if (educationInfo == null) {
			educationInfo = new EducationInfo();

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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public ParentInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(ParentInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

	public List<Integer> getBirthdayYears() {
		if (this.birthdayYears == null || this.birthdayYears.size() == 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date date = new Date();
			int year = Integer.parseInt(dateFormat.format(date));
			for (int i = 1950; i < year - 17; i++) {
				this.birthdayYears.add(i);
			}
		}

		return birthdayYears;
	}

	public void setBirthdayYears(List<Integer> birthdayYears) {
		this.birthdayYears = birthdayYears;
	}

	public HighSchool getHighSchool() {
		return highSchool;
	}

	public void setHighSchool(HighSchool highSchool) {
		this.highSchool = highSchool;
	}

	public List<HighSchoolType> getHighSchoolTypes() {
		return highSchoolTypes;
	}

	public void setHighSchoolTypes(List<HighSchoolType> highSchoolTypes) {
		this.highSchoolTypes = highSchoolTypes;
	}

	public List<GradingSystem> getGradingSystems() {
		return gradingSystems;
	}

	public void setGradingSystems(List<GradingSystem> gradingSystems) {
		this.gradingSystems = gradingSystems;
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

	public EducationInfoService getEducationInfoService() {
		return educationInfoService;
	}

	public void setEducationInfoService(
			EducationInfoService educationInfoService) {
		this.educationInfoService = educationInfoService;
	}

	public HighSchoolService getHighSchoolService() {
		return highSchoolService;
	}

	public void setHighSchoolService(HighSchoolService highSchoolService) {
		this.highSchoolService = highSchoolService;
	}

	public ParentInfoService getParentInfoService() {
		return parentInfoService;
	}

	public void setParentInfoService(ParentInfoService parentInfoService) {
		this.parentInfoService = parentInfoService;
	}

	public ContactInfoService getContactInfoService() {
		return contactInfoService;
	}

	public void setContactInfoService(ContactInfoService contactInfoService) {
		this.contactInfoService = contactInfoService;
	}

	public InitStudentInfoService getInitStudentInfoService() {
		return initStudentInfoService;
	}

	public void setInitStudentInfoService(
			InitStudentInfoService initStudentInfoService) {
		this.initStudentInfoService = initStudentInfoService;
	}

	public String onFlowProcess(FlowEvent event) {

		return event.getNewStep();

	}

	public void saveInitStudentInfo() throws IOException {

		try {

			getContact().setUser(getLoggedUser());
			getParentInfo().setUser(getLoggedUser());
			getHighSchool().setUser(getLoggedUser());
			getEducationInfo().setUser(getLoggedUser());

			Date registeredDate = new Date();

			int differenceEndRegisterHighSchool = Days.daysBetween(
					new DateTime(getHighSchool().getEndDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceEndRegisterHighSchool > 0) {

				if (getIsStudent()) {
					getEducationInfo().setEndDate(null);

					int differenceStartRegister = Days.daysBetween(
							new DateTime(getEducationInfo().getStartDate()),
							new DateTime(registeredDate)).getDays();
					if (differenceStartRegister > 0) {
						
						if (getInitStudentInfoService().saveInitStudentInfo(getContact(),
								getParentInfo(), getHighSchool(), getEducationInfo())) {
							FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"index.xhtml?user="
											+ getLoggedUser().getTcno());

						} else {
							FacesMessage fm = new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Yeni öğrenci kaydı tamamlanamadı.",
									"Lütfen daha sonra yeniden deneyiniz.");
							FacesContext.getCurrentInstance().addMessage(null, fm);

						}
						

					} else {
						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Üniversite başlangıç tarihi geçmiş zamana ait olmalıdır.",
								"Lütfen yeniden deneyiniz.");
						FacesContext.getCurrentInstance().addMessage(null, fm);

					}
				} else {
					int differenceStartEnd = Days.daysBetween(
							new DateTime(getEducationInfo().getStartDate()),
							new DateTime(getEducationInfo().getEndDate()))
							.getDays();

					int differenceStartRegister = Days.daysBetween(
							new DateTime(getEducationInfo().getStartDate()),
							new DateTime(registeredDate)).getDays();

					if (differenceStartEnd > 0 && differenceStartRegister > 0) {
						
						
						if (getInitStudentInfoService().saveInitStudentInfo(getContact(),
								getParentInfo(), getHighSchool(), getEducationInfo())) {
							FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"index.xhtml?user="
											+ getLoggedUser().getTcno());

						} else {
							FacesMessage fm = new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Yeni öğrenci kaydı tamamlanamadı.",
									"Lütfen daha sonra yeniden deneyiniz.");
							FacesContext.getCurrentInstance().addMessage(null, fm);

						}


					} else {
						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Üniversite başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
								"Lütfen yeniden deneyiniz.");
						FacesContext.getCurrentInstance().addMessage(null, fm);

					}

				}

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Lise bitiş tarihi geçmiş zamana ait olmalıdır.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
