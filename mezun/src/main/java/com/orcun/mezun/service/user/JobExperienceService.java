package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.JobExperienceDAO;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.JobExperience;
import com.orcun.mezun.model.Position;
import com.orcun.mezun.model.Sector;
import com.orcun.mezun.model.User;
import com.orcun.mezun.model.WorkingType;

@Service
public class JobExperienceService {
	
	@Autowired
	private JobExperienceDAO jobExperienceDAO;


	public JobExperienceDAO getJobExperienceDAO() {
		return jobExperienceDAO;
	}

	public void setJobExperienceDAO(JobExperienceDAO jobExperienceDAO) {
		this.jobExperienceDAO = jobExperienceDAO;
	}

	public void updateJobExperience(JobExperience jobExperience) {
		getJobExperienceDAO().updateJobExperience(jobExperience);
	}

	public void addJobExperience(JobExperience jobExperience) {
		getJobExperienceDAO().addJobExperience(jobExperience);
	}

	public List<Country> allCountries() {
		return getJobExperienceDAO().allCountries();
	}

	public List<City> allCities() {
		return getJobExperienceDAO().allCities();
	}

	public List<Position> allPositions() {
		return getJobExperienceDAO().allPositions();
	}

	public List<Sector> allSectors() {
		return getJobExperienceDAO().allSectors();
	}

	public List<WorkingType> allWorkingTypes() {
		return getJobExperienceDAO().allWorkingTypes();
	}

	public List<JobExperience> allJobExperience(User loggedUser) {
		return getJobExperienceDAO().allJobExperience(loggedUser);
	}

}
