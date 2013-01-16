package com.orcun.mezun.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="area_of_interest")
public class AreaOfInterest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="area_of_interest_title",nullable=false,length=200)
	private String areaOfInterestTitle;
	
	@Column(name="area_of_interest_name",nullable=false,length=200)
	private String areaOfInterestName;
	
	@Column(name="experience_level",nullable=false)
	private Integer experienceLevel;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaOfInterestTitle() {
		return areaOfInterestTitle;
	}

	public void setAreaOfInterestTitle(String areaOfInterestTitle) {
		this.areaOfInterestTitle = areaOfInterestTitle;
	}

	public String getAreaOfInterestName() {
		return areaOfInterestName;
	}

	public void setAreaOfInterestName(String areaOfInterestName) {
		this.areaOfInterestName = areaOfInterestName;
	}

	public Integer getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(Integer experienceLevel) {
		this.experienceLevel = experienceLevel;
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
		AreaOfInterest other = (AreaOfInterest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
