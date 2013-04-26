package com.orcun.mezun.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="high_school")
public class HighSchool implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@Column(name="high_school_name",nullable=false,length=200)
	private String highSchoolName;
	
	@Column(name="high_school_department",nullable=false,length=200)
	private String highSchoolDepartment;
	
	@Column(name="end_year",nullable=false)
	private Integer endYear;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="high_school_type_id")
	private HighSchoolType highSchoolType;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="grading_system_id")
	private GradingSystem gradingSystem;
	
	@Column(name="graduation_degree")
	private String graduationDegree;
	
	@Column(name="ytu_ce_order_of_preference",nullable=true)
	private Integer ytuCeOrderOfPreference;
	
	@Column(name="oss_first_choise",nullable=true)
	private String ossFirstChoise;
	
	@Column(name="oss_second_choise",nullable=true)
	private String ossSecondChoise;
	
	@Column(name="oss_third_choise",nullable=true)
	private String ossThirdChoise;
	
	public String getHighSchoolName() {
		return highSchoolName;
	}

	public void setHighSchoolName(String highSchoolName) {
		this.highSchoolName = highSchoolName;
	}

	public String getHighSchoolDepartment() {
		return highSchoolDepartment;
	}

	public void setHighSchoolDepartment(String highSchoolDepartment) {
		this.highSchoolDepartment = highSchoolDepartment;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public HighSchoolType getHighSchoolType() {
		return highSchoolType;
	}

	public void setHighSchoolType(HighSchoolType highSchoolType) {
		this.highSchoolType = highSchoolType;
	}

	public GradingSystem getGradingSystem() {
		return gradingSystem;
	}

	public void setGradingSystem(GradingSystem gradingSystem) {
		this.gradingSystem = gradingSystem;
	}

	public String getGraduationDegree() {
		return graduationDegree;
	}

	public void setGraduationDegree(String graduationDegree) {
		this.graduationDegree = graduationDegree;
	}

	public Integer getYtuCeOrderOfPreference() {
		return ytuCeOrderOfPreference;
	}

	public void setYtuCeOrderOfPreference(Integer ytuCeOrderOfPreference) {
		this.ytuCeOrderOfPreference = ytuCeOrderOfPreference;
	}

	public String getOssFirstChoise() {
		return ossFirstChoise;
	}

	public void setOssFirstChoise(String ossFirstChoise) {
		this.ossFirstChoise = ossFirstChoise;
	}

	public String getOssSecondChoise() {
		return ossSecondChoise;
	}

	public void setOssSecondChoise(String ossSecondChoise) {
		this.ossSecondChoise = ossSecondChoise;
	}

	public String getOssThirdChoise() {
		return ossThirdChoise;
	}

	public void setOssThirdChoise(String ossThirdChoise) {
		this.ossThirdChoise = ossThirdChoise;
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
		HighSchool other = (HighSchool) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	

}
