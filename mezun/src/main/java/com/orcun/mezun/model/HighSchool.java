package com.orcun.mezun.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="high_school")
public class HighSchool implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="high_school_name",nullable=false,length=200)
	private String highSchoolName;
	
	@Column(name="high_school_department",nullable=false,length=200)
	private String highSchoolDepartment;
	
	@Column(name="end_date",nullable=true)
	private Date endDate=new Date();
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="high_school_type_id")
	private HighSchoolType highSchoolType;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="grading_system_id")
	private GradingSystem gradingSystem;
	
	@Column(name="graduation_degree")
	private Integer graduationDegree;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Integer getGraduationDegree() {
		return graduationDegree;
	}

	public void setGraduationDegree(Integer graduationDegree) {
		this.graduationDegree = graduationDegree;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
