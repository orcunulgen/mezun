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
import javax.faces.context.Flash;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.context.SecurityContext;

import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.JobExperience;
import com.orcun.mezun.model.Position;
import com.orcun.mezun.model.Sector;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.WorkingType;
import com.orcun.mezun.service.user.JobExperienceService;

@ManagedBean
@ViewScoped
public class JobExperienceView implements Serializable {

	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private JobExperience jobExperience;

	private Boolean jobExperienceIsContinue = true;

	private List<Country> countries = new ArrayList<Country>();

	private List<City> jobAddCities = new ArrayList<City>();

	private List<City> jobUpdateCities = new ArrayList<City>();

	private List<Position> positions = new ArrayList<Position>();

	private List<Sector> sectors = new ArrayList<Sector>();

	private List<WorkingType> workingTypes = new ArrayList<WorkingType>();

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();
	private JobExperience selectedJobExperience;
	private Boolean selectedJobExperienceIsContinue;

	@ManagedProperty(value = "#{jobExperienceService}")
	private JobExperienceService jobExperienceService;

	@PostConstruct
	public void init() {
		countries = jobExperienceService.allCountries();
		// cities = jobExperienceService.allCities();
		positions = jobExperienceService.allPositions();
		sectors = jobExperienceService.allSectors();
		workingTypes = jobExperienceService.allWorkingTypes();
		jobExperiences = jobExperienceService.allJobExperience(getLoggedUser());

		if (jobExperience == null) {
			jobExperience = new JobExperience();

		}
	}

	public void initSelectedJobEx(JobExperience selectedJobExperience) {
		this.selectedJobExperience = selectedJobExperience;
		if (getSelectedJobExperience().getEndDate() == null) {
			this.selectedJobExperienceIsContinue = true;
		} else {
			this.selectedJobExperienceIsContinue = false;
		}
		updateJobChangeCountry();
	}

	public void deleteSelectedJobEx(JobExperience selectedJobExperience)
			throws IOException {
		initSelectedJobEx(selectedJobExperience);
		getJobExperienceService().deleteJobExperience(
				getSelectedJobExperience());

		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"job_experience.xhtml?u="
								+ getLoggedUser().getTcno());

	}

	public JobExperience getJobExperience() {

		return jobExperience;
	}

	public void setJobExperience(JobExperience jobExperience) {
		this.jobExperience = jobExperience;
	}

	public Boolean getJobExperienceIsContinue() {
		return jobExperienceIsContinue;
	}

	public void setJobExperienceIsContinue(Boolean jobExperienceIsContinue) {
		this.jobExperienceIsContinue = jobExperienceIsContinue;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<City> getJobAddCities() {
		return jobAddCities;
	}

	public void setJobAddCities(List<City> jobAddCities) {
		this.jobAddCities = jobAddCities;
	}

	public List<City> getJobUpdateCities() {
		return jobUpdateCities;
	}

	public void setJobUpdateCities(List<City> jobUpdateCities) {
		this.jobUpdateCities = jobUpdateCities;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public List<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public List<WorkingType> getWorkingTypes() {
		return workingTypes;
	}

	public void setWorkingTypes(List<WorkingType> workingTypes) {
		this.workingTypes = workingTypes;
	}

	public List<JobExperience> getJobExperiences() {
		return jobExperiences;
	}

	public void setJobExperiences(List<JobExperience> jobExperiences) {
		this.jobExperiences = jobExperiences;
	}

	public JobExperience getSelectedJobExperience() {
		return selectedJobExperience;
	}

	public void setSelectedJobExperience(JobExperience selectedJobExperience) {
		this.selectedJobExperience = selectedJobExperience;
	}

	public Boolean getSelectedJobExperienceIsContinue() {
		return selectedJobExperienceIsContinue;
	}

	public void setSelectedJobExperienceIsContinue(
			Boolean selectedJobExperienceIsContinue) {
		this.selectedJobExperienceIsContinue = selectedJobExperienceIsContinue;
	}

	public JobExperienceService getJobExperienceService() {
		return jobExperienceService;
	}

	public void setJobExperienceService(
			JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}

	public void addJobChangeCountry() {
		jobAddCities = getJobExperienceService().allCities(
				getJobExperience().getCountry());
	}

	public void updateJobChangeCountry() {
		jobUpdateCities = getJobExperienceService().allCities(
				getSelectedJobExperience().getCountry());
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public String addJobExperience() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getJobExperienceIsContinue()) {
			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getJobExperience().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartRegister > 0) {

				getJobExperience().setEndDate(null);
				getJobExperience().setUser(getLoggedUser());
				getJobExperience().setRegisteredDate(registeredDate);
				getJobExperienceService().addJobExperience(getJobExperience());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} else {
			int differenceStartEnd = Days.daysBetween(
					new DateTime(getJobExperience().getStartDate()),
					new DateTime(getJobExperience().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getJobExperience().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister > 0) {

				getJobExperience().setUser(getLoggedUser());
				getJobExperience().setRegisteredDate(registeredDate);
				getJobExperienceService().addJobExperience(getJobExperience());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla tamamlandı.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		}

		return "job_experience.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();

	}

	public String updateJobExperience() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);

		if (getSelectedJobExperienceIsContinue()) {

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedJobExperience().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartRegister > 0) {

				getSelectedJobExperience().setEndDate(null);
				getJobExperienceService().updateJobExperience(
						getSelectedJobExperience());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		} else {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getSelectedJobExperience().getStartDate()),
					new DateTime(getSelectedJobExperience().getEndDate()))
					.getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedJobExperience().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister > 0) {

				getJobExperienceService().updateJobExperience(
						getSelectedJobExperience());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Kaydınız başarıyla güncellendi.", "");

				FacesContext.getCurrentInstance().addMessage(null, fm);

			} else {
				FacesMessage fm = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Başlangıç tarihi geçmiş zamana ait olmalıdır ve başlangıç tarihi bitiş tarihinden ilerde olamaz.",
						"Lütfen yeniden deneyiniz.");
				FacesContext.getCurrentInstance().addMessage(null, fm);

			}

		}

		return "job_experience.xhtml?faces-redirect=true&u="
				+ getLoggedUser().getTcno();
	}
}
