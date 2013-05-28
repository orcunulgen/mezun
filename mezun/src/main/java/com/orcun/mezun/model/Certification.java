package com.orcun.mezun.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.util.MyURLUtil;

@Entity
@Table(name="certification")
public class Certification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="certificate_name",nullable=false,length=200)
	private String certificateName;
	
	@Column(name="certificate_date",nullable=false)
	private Date certificateDate=new Date();
	
	@Column(name="institution",nullable=false,length=200)
	private String institution;
	
	@Column(name="description",nullable=false,length=200)
	private String description;
	
	@Column(name="file_path",nullable=true,length=200)
	private String filePath;
	
	@SuppressWarnings("unused")
	@Transient
	private String fileURL;
	
	@OneToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) throws UnsupportedEncodingException {
		this.certificateName = new String(certificateName.getBytes("ISO-8859-1"), "UTF-8");
	}

	public Date getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) throws UnsupportedEncodingException {
		this.institution =new String(institution.getBytes("ISO-8859-1"), "UTF-8");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws UnsupportedEncodingException {
		this.description =new String(description.getBytes("ISO-8859-1"), "UTF-8");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFileURL() throws MalformedURLException {
		return MyURLUtil.getBaseURL(FacesContext.getCurrentInstance())
				+ UploadedFileDirectory.CERTIFICATES_PATH.getPath()
				+"/"
				+ getFilePath();
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
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
		Certification other = (Certification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	

}
