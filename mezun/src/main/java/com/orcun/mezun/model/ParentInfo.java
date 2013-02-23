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
@Table(name="parent_info")
public class ParentInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@Column(name="father_name",nullable=false,length=200)
	private String fatherName;
	
	@Column(name="mother_name",nullable=false,length=200)
	private String motherName;
	
	@Column(name="father_job",nullable=false,length=200)
	private String fatherJob;
	
	@Column(name="mother_job",nullable=false,length=200)
	private String motherJob;
	
	@Column(name="father_birthday_year",nullable=false)
	private Integer fatherBirthdayYear;
	
	@Column(name="mother_birthday_year",nullable=false)
	private Integer motherBirthdayYear;
	
	@Column(name="parent_monthly_income",nullable=false)
	private Integer parentMonthlyIncome;
	
	@Column(name="parent_phone_number",nullable=false,length=200)
	private String parentPhoneNumber;
	
	@Column(name="parent_address",nullable=false,length=200)
	private String parentAddress;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherJob() {
		return fatherJob;
	}

	public void setFatherJob(String fatherJob) {
		this.fatherJob = fatherJob;
	}

	public String getMotherJob() {
		return motherJob;
	}

	public void setMotherJob(String motherJob) {
		this.motherJob = motherJob;
	}

	public Integer getFatherBirthdayYear() {
		return fatherBirthdayYear;
	}

	public void setFatherBirthdayYear(Integer fatherBirthdayYear) {
		this.fatherBirthdayYear = fatherBirthdayYear;
	}

	public Integer getMotherBirthdayYear() {
		return motherBirthdayYear;
	}

	public void setMotherBirthdayYear(Integer motherBirthdayYear) {
		this.motherBirthdayYear = motherBirthdayYear;
	}

	public Integer getParentMonthlyIncome() {
		return parentMonthlyIncome;
	}

	public void setParentMonthlyIncome(Integer parentMonthlyIncome) {
		this.parentMonthlyIncome = parentMonthlyIncome;
	}

	public String getParentPhoneNumber() {
		return parentPhoneNumber;
	}

	public void setParentPhoneNumber(String parentPhoneNumber) {
		this.parentPhoneNumber = parentPhoneNumber;
	}

	public String getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(String parentAddress) {
		this.parentAddress = parentAddress;
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
		ParentInfo other = (ParentInfo) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	

}
