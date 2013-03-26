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
import com.orcun.mezun.model.University;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.user.ContactInfoService;
import com.orcun.mezun.service.user.EducationInfoService;
import com.orcun.mezun.service.user.InitAlumniInfoService;

@ManagedBean
@ViewScoped
public class InitAlumniInfoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	// -------------------contact_info-----------------------
	private Contact contact;

	// ------------------------------------------------------

	// ---------------university_info--------------------------
	private EducationInfo educationInfo;

	private Boolean isStudent = false;

	private List<University> universities = new ArrayList<University>();

	private List<Faculty> universityAddFaculties = new ArrayList<Faculty>();

	private List<Department> facultyAddDepartments = new ArrayList<Department>();

	private List<EducationLevel> educationLevels = new ArrayList<EducationLevel>();

	private List<GradingSystem> gradingSystems = new ArrayList<GradingSystem>();

	// --------------------------------------------------------

	@ManagedProperty(value = "#{educationInfoService}")
	private EducationInfoService educationInfoService;

	@ManagedProperty(value = "#{contactInfoService}")
	private ContactInfoService contactInfoService;

	@ManagedProperty(value = "#{initAlumniInfoService}")
	private InitAlumniInfoService initAlumniInfoService;

	@PostConstruct
	public void init() {
		if (contact == null) {
			this.contact = new Contact();
		}

		gradingSystems = educationInfoService.allGradingSystems();

		universities = educationInfoService.allUniversities();
		educationLevels = educationInfoService.allEducationLevels();

		if (educationInfo == null) {
			educationInfo = new EducationInfo();
			educationInfo.setUniversity(getEducationInfoService().getYTU((long) 1));
			educationInfo.setFaculty(getEducationInfoService().getYTUFaculty((long)1));
			educationInfo.setDepartment(getEducationInfoService().getYTUCE((long)1));
			
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
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

	public ContactInfoService getContactInfoService() {
		return contactInfoService;
	}

	public void setContactInfoService(ContactInfoService contactInfoService) {
		this.contactInfoService = contactInfoService;
	}

	public InitAlumniInfoService getInitAlumniInfoService() {
		return initAlumniInfoService;
	}

	public void setInitAlumniInfoService(
			InitAlumniInfoService initAlumniInfoService) {
		this.initAlumniInfoService = initAlumniInfoService;
	}

	public String onFlowProcess(FlowEvent event) {

		return event.getNewStep();

	}

	public void saveInitAlumniInfo() throws IOException {

		try {

			getContact().setUser(getLoggedUser());
			getEducationInfo().setUser(getLoggedUser());

			Date registeredDate = new Date();

			if (getIsStudent()) {
				getEducationInfo().setEndDate(null);

				int differenceStartRegister = Days.daysBetween(
						new DateTime(getEducationInfo().getStartDate()),
						new DateTime(registeredDate)).getDays();
				if (differenceStartRegister > 0) {

					if (getInitAlumniInfoService().saveInitAlumniInfo(
							getContact(), getEducationInfo())) {
						FacesContext
								.getCurrentInstance()
								.getExternalContext()
								.redirect(
										"index.xhtml?user="
												+ getLoggedUser().getTcno());

					} else {
						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Yeni mezun kaydı tamamlanamadı.",
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

					if (getInitAlumniInfoService().saveInitAlumniInfo(
							getContact(), getEducationInfo())) {
						FacesContext
								.getCurrentInstance()
								.getExternalContext()
								.redirect(
										"index.xhtml?user="
												+ getLoggedUser().getTcno());

					} else {
						FacesMessage fm = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Yeni mezun kaydı tamamlanamadı.",
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

		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}

}
