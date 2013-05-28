package com.orcun.mezun.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="admin_basic_info")
public class AdminBasicInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
		
	@OneToOne(fetch=FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private University university;
	
	@OneToOne(fetch=FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Faculty faculty;
	
	@OneToOne(fetch=FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Department department;


	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminBasicInfo other = (AdminBasicInfo) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
}
