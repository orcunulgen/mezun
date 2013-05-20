package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.Event;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class EventDAO{
	
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
	
	public Long addEvent(Event event) {
		Long savedEvent=(Long)getSession().save(event);
		return savedEvent;
	}	
	
	public void updateEvent(Event event){
		getSession().update(event);
	}

	@SuppressWarnings("unchecked")
	public List<Event> allEvent(User loggedUser) {
		return getSession().createCriteria(Event.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}
	
	public void deleteEvent(Event selectedEvent) {
		getSession().createSQLQuery("delete from event where id = :id") 
	    .setParameter("id", selectedEvent.getId())
	    .executeUpdate();
	}
	
	
}
