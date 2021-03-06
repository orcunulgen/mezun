package com.orcun.mezun.model;

import java.io.Serializable;
import java.net.MalformedURLException;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.util.MyURLUtil;


@Entity
@Table(name="education_info")
public class EducationInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="education_level_id",nullable=false)
	private EducationLevel educationLevel;
	
	@Column(name="start_year",nullable=false)
	private Integer startYear;
	
	@Column(name="end_year",nullable=true)
	private Integer endYear;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false)
	private University university;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false)
	private Faculty faculty;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(nullable=false)
	private Department department;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="grading_system_id",nullable=false)
	private GradingSystem gradingSystem;
	
	@Column(name="graduation_degree")
	private String graduationDegree;
	
	@Column(name="class_info",nullable=true)
	private Integer classInfo;
	
	@Column(name="transcript_path",nullable=true,length=200)
	private String transcriptPath;
	
	@SuppressWarnings("unused")
	@Transient
	private String transcriptFileURL;
	
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

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}


	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

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

	public Integer getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(Integer classInfo) {
		this.classInfo = classInfo;
	}

	public String getTranscriptPath() {
		return transcriptPath;
	}

	public void setTranscriptPath(String transcriptPath) {
		this.transcriptPath = transcriptPath;
	}

	public String getTranscriptFileURL() throws MalformedURLException {
		return MyURLUtil.getBaseURL(FacesContext.getCurrentInstance())
				+ UploadedFileDirectory.TRANSCRIPT_PATH.getPath()
				+"/"
				+ getTranscriptPath();
	}

	public void setTranscriptFileURL(String transcriptFileURL) {
		this.transcriptFileURL = transcriptFileURL;
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
		EducationInfo other = (EducationInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
