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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.orcun.mezun.model.enums.UploadedFileDirectory;
import com.orcun.mezun.util.MyURLUtil;


@Entity
@Table(name="photo")
public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="place",nullable=true,length=200)
	private String place;
	
	@Column(name="description",nullable=true,length=200)
	private String description;
	
	@Column(name="date",nullable=false)
	private Date date=new Date();
	
	@Column(name="photo_path",nullable=false,length=200)
	private String photoPath;
	
	@SuppressWarnings("unused")
	@Transient
	private String photoURL;
	
	@OneToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name="album_id",nullable=false)
	private PhotoAlbum photoAlbum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) throws UnsupportedEncodingException {
		this.place = new String(place.getBytes("ISO-8859-1"), "UTF-8");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws UnsupportedEncodingException {
		this.description = new String(description.getBytes("ISO-8859-1"), "UTF-8");
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoURL() throws MalformedURLException {
		 return MyURLUtil.getBaseURL(FacesContext.getCurrentInstance())
				+ UploadedFileDirectory.ALBUM_PHOTO_PATH.getPath()
				+"/"
				+ getPhotoPath();
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
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
		Photo other = (Photo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
