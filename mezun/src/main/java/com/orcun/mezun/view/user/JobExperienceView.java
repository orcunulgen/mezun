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

	private List<Country> countries = new ArrayList<Country>();

	private List<City> jobAddCities = new ArrayList<City>();
	
	private List<City> jobUpdateCities = new ArrayList<City>();

	private List<Position> positions = new ArrayList<Position>();

	private List<Sector> sectors = new ArrayList<Sector>();

	private List<WorkingType> workingTypes = new ArrayList<WorkingType>();

	private List<JobExperience> jobExperiences = new ArrayList<JobExperience>();
	private JobExperience selectedJobExperience;

	@ManagedProperty(value = "#{jobExperienceService}")
	private JobExperienceService jobExperienceService;

	@PostConstruct
	public void init() {
		countries = jobExperienceService.allCountries();
		//cities = jobExperienceService.allCities();
		positions = jobExperienceService.allPositions();
		sectors = jobExperienceService.allSectors();
		workingTypes = jobExperienceService.allWorkingTypes();
		jobExperiences=jobExperienceService.allJobExperience(getLoggedUser());
	}

	public User getLoggedUser() {
		SecurityContext securityContext = (SecurityContext) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("SPRING_SECURITY_CONTEXT");

		this.loggedUser = (User) securityContext.getAuthentication()
				.getPrincipal();

		return loggedUser;

	}

	public JobExperience getJobExperience() {
		if (jobExperience == null) {
			jobExperience = new JobExperience();

		}
		return jobExperience;
	}

	public void setJobExperience(JobExperience jobExperience) {
		this.jobExperience = jobExperience;
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

	public JobExperienceService getJobExperienceService() {
		return jobExperienceService;
	}

	public void setJobExperienceService(
			JobExperienceService jobExperienceService) {
		this.jobExperienceService = jobExperienceService;
	}
	
	public void initSelectedJobEx(JobExperience selectedJobExperience){
		this.selectedJobExperience=selectedJobExperience;
		updateJobChangeCountry();
	}
	
	public void addJobChangeCountry(){
		jobAddCities=getJobExperienceService().allCities(getJobExperience().getCountry());
	}
	public void updateJobChangeCountry(){
		jobUpdateCities=getJobExperienceService().allCities(getSelectedJobExperience().getCountry());
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

	public void addJobExperience() throws IOException {
		try {

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
								"job_experience.xhtml?user="
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
	
	public void updateJobExperience() throws IOException {
		try {

			int differenceStartEnd = Days.daysBetween(
					new DateTime(getSelectedJobExperience().getStartDate()),
					new DateTime(getSelectedJobExperience().getEndDate())).getDays();

			Date registeredDate = new Date();

			int differenceStartRegister = Days.daysBetween(
					new DateTime(getSelectedJobExperience().getStartDate()),
					new DateTime(registeredDate)).getDays();

			if (differenceStartEnd > 0 && differenceStartRegister > 0) {

				getJobExperienceService().updateJobExperience(getSelectedJobExperience());

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
								"job_experience.xhtml?user="
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
