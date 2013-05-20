package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Announcement;
import com.orcun.mezun.model.AnnouncementType;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class AnnouncementDAO{
	
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
	
	public Long addAnnouncement(Announcement announcement) {
		Long savedAnnouncement=(Long) getSession().save(announcement);
		return savedAnnouncement;
	}	
	
	public void updateAnnouncement(Announcement announcement){
		getSession().update(announcement);
	}

	@SuppressWarnings("unchecked")
	public List<AnnouncementType> allAnnouncementTypes() {
		return getSession().createCriteria(AnnouncementType.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Announcement> allAnnouncement(User loggedUser) {
		return getSession().createCriteria(Announcement.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deleteAnnouncement(Announcement selectedAnnouncement) {
		getSession().createSQLQuery("delete from announcement where id = :id") 
	    .setParameter("id", selectedAnnouncement.getId())
	    .executeUpdate();
	}
	
	
}
