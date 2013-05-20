package com.orcun.mezun.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcun.mezun.dao.user.PhotoDAO;
import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.User;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoDAO photoDAO;

	public PhotoDAO getPhotoDAO() {
		return photoDAO;
	}

	public void setPhotoDAO(PhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}

	public Long addPhoto(Photo photo) {
		return getPhotoDAO().addPhoto(photo);
	}
	
	public void addPhotoAlbum(PhotoAlbum photoAlbum) {
		getPhotoDAO().addPhotoAlbum(photoAlbum);
	}
	
	public void updatePhoto(Photo photo) {
		getPhotoDAO().updatePhoto(photo);
	}

	public void updatePhotoAlbum(PhotoAlbum photoAlbum) {
		getPhotoDAO().updatePhotoAlbum(photoAlbum);
	}
	public List<PhotoAlbum> allPhotoAlbum(User loggedUser) {
		if (loggedUser != null) {
			return getPhotoDAO().allPhotoAlbum(loggedUser);
		} else {
			return null;
		}
	}

	public PhotoAlbum findPhotoAlbumById(Long photoAlbumId) {
		return getPhotoDAO().findPhotoAlbumById(photoAlbumId);
	}

}
