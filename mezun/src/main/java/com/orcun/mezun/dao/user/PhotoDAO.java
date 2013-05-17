package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Photo;
import com.orcun.mezun.model.PhotoAlbum;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class PhotoDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	public void addPhoto(Photo photo) {
		getSession().save(photo);
	}
	
	public void addPhotoAlbum(PhotoAlbum photoAlbum) {
		getSession().save(photoAlbum);
	}
	
	public void updatePhoto(Photo photo){
		getSession().update(photo);
	}
	
	public void updatePhotoAlbum(PhotoAlbum photoAlbum){
		getSession().update(photoAlbum);
	}

	@SuppressWarnings("unchecked")
	public List<PhotoAlbum> allPhotoAlbum(User loggedUser) {
		return getSession().createCriteria(PhotoAlbum.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deletePhoto(Photo selectedPhoto) {
		getSession().delete(selectedPhoto);
	}
	
	public void deletePhotoAlbum(PhotoAlbum selectedPhotoAlbum) {
		getSession().delete(selectedPhotoAlbum);
	}

	public PhotoAlbum findPhotoAlbumById(Long photoAlbumId) {
		return (PhotoAlbum) getSession().createCriteria(PhotoAlbum.class)
				.add(Restrictions.eq("id", photoAlbumId)).uniqueResult();
	}
	
	
}
