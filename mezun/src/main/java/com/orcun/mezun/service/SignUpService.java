package com.orcun.mezun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.SignUpDAO;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;



@Service
public class SignUpService{
	
	@Autowired
	private SignUpDAO signUpDAO;
	
	
	
	public void addUser(User user) {
		
		getSignUpDAO().addUser(user);
	
	}

	public SignUpDAO getSignUpDAO() {
		return signUpDAO;
	}

	public void setSignUpDAO(SignUpDAO signUpDAO) {
		this.signUpDAO = signUpDAO;
	}
	
	public List<Country> allCountries() {
		return getSignUpDAO().allCountries();
	}

	public List<City> allCities(Country country) {
		return getSignUpDAO().allCities(country);
	}
	
	public boolean areThereSameUsername(String username){
		if(signUpDAO.areThereSameUsername(username)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean areThereSameEmail(String email){
		if(signUpDAO.areThereSameEmail(email)){
			return true;
		}else{
			return false;
		}
	}
	
	public Role getRoleInfo(String role){
		return signUpDAO.getRoleInfo(role);
	}

	public List<Role> getRoles() {
		return signUpDAO.getRoles();
	}

	public boolean areThereSameTcno(Long tcno) {
		
		if(signUpDAO.areThereSameTcno(tcno)){
			return true;
		}else{
			return false;
		}
	}

}
