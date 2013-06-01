package com.orcun.mezun.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;

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
@Table(name = "announcement")
public class Announcement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, length = 200)
	private String title;

	@Column(name = "description", nullable = false, length = 200)
	private String description;

	@Column(name = "registered_date", nullable = false)
	private Date registeredDate = new Date();

	@Column(name = "poster_path", nullable = true, length = 200)
	private String posterPath;
	
	@SuppressWarnings("unused")
	@Transient
	private String posterURL;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "announcement_type_id",nullable=false)
	private AnnouncementType announcementType;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable=false)
	private User user;

	public Long getId() {
		return id;
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
		Announcement other = (Announcement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws UnsupportedEncodingException {
		this.title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws UnsupportedEncodingException {
		this.description = new String(description.getBytes("ISO-8859-1"),
				"UTF-8");
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getPosterPath(){

		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getPosterURL() throws MalformedURLException {
		return MyURLUtil.getBaseURL(FacesContext.getCurrentInstance())
				+ UploadedFileDirectory.ANNOUNCEMENT_POSTER_PATH.getPath()
				+"/"
				+ getPosterPath();
	}
	
	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}

	public AnnouncementType getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(AnnouncementType announcementType) {
		this.announcementType = announcementType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
