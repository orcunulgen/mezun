package com.orcun.mezun.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.admin.StudentDAO;
import com.orcun.mezun.model.City;
import com.orcun.mezun.model.Country;
import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;

@Service
public class StudentService {
	
	@Autowired
	private StudentDAO studentDAO;


	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	public void updateStudent(User student) {
		getStudentDAO().updateStudent(student);
	}

	public List<Country> allCountries() {
		return getStudentDAO().allCountries();
	}

	public List<City> allCities(Country country) {
		return getStudentDAO().allCities(country);
	}

	public List<User> allStudents(List<Role> roles) {
		return getStudentDAO().allStudents(roles);
	}
	
	public void deleteStudent(User selectedStudent) {
		getStudentDAO().deleteStudent(selectedStudent);
		
	}
}
