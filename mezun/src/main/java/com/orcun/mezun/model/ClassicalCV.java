package com.orcun.mezun.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="classical_cv")
public class ClassicalCV implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@Column(name="cv_path",nullable=false,length=200)
	private String cvPath;
	
	@Column(name="cv_file_name",nullable=false,length=200)
	private String cvFileName;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCvPath() {
		return cvPath;
	}

	public void setCvPath(String cvPath) {
		this.cvPath = cvPath;
	}

	public String getCvFileName() {
		return cvFileName;
	}

	public void setCvFileName(String cvFileName) {
		this.cvFileName = cvFileName;
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
		ClassicalCV other = (ClassicalCV) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
