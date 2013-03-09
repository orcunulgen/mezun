package com.orcun.mezun.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.admin.AlumniDAO;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;

@Service
public class AlumniService {
	
	@Autowired
	private AlumniDAO alumniDAO;


	public AlumniDAO getAlumniDAO() {
		return alumniDAO;
	}

	public void setAlumniDAO(AlumniDAO alumniDAO) {
		this.alumniDAO = alumniDAO;
	}

	public void updateAlumni(User alumni) {
		getAlumniDAO().updateAlumni(alumni);
	}

	public List<Country> allCountries() {
		return getAlumniDAO().allCountries();
	}

	public List<City> allCities(Country country) {
		return getAlumniDAO().allCities(country);
	}

	public List<User> allAlumnis(List<Role> roles) {
		return getAlumniDAO().allAlumnis(roles);
	}
	
	public void deleteAlumni(User selectedAlumni) {
		getAlumniDAO().deleteAlumni(selectedAlumni);
		
	}
}
