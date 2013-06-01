package com.orcun.mezun.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="foreign_language")
public class ForeignLanguage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false)
	private Language language;
	
	@Column(name="writing_rank",nullable=false)
	private Integer writingRank;
	
	@Column(name="reading_rank",nullable=false)
	private Integer readingRank;
	
	@Column(name="speaking_rank",nullable=false)
	private Integer speakingRank;
	
	@Column(name="registered_date",nullable=false)
	private Date registeredDate=new Date();
	
	@OneToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(nullable=false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Integer getWritingRank() {
		return writingRank;
	}

	public void setWritingRank(Integer writingRank) {
		this.writingRank = writingRank;
	}

	public Integer getReadingRank() {
		return readingRank;
	}

	public void setReadingRank(Integer readingRank) {
		this.readingRank = readingRank;
	}

	public Integer getSpeakingRank() {
		return speakingRank;
	}

	public void setSpeakingRank(Integer speakingRank) {
		this.speakingRank = speakingRank;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
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
		ForeignLanguage other = (ForeignLanguage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
