package com.orcun.mezun.dao.user;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.orcun.mezun.model.AreaOfInterest;
import com.orcun.mezun.model.User;


@Repository
@Transactional
public class AreaOfInterestDAO{
	
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
	
	public void addAreaOfInterest(AreaOfInterest areaOfInterest) {
		getSession().save(areaOfInterest);
	}	
	
	public void updateAreaOfInterest(AreaOfInterest areaOfInterest){
		getSession().update(areaOfInterest);
	}


	@SuppressWarnings("unchecked")
	public List<AreaOfInterest> allAreaOfInterest(User loggedUser) {
		return getSession().createCriteria(AreaOfInterest.class)
				.add(Restrictions.eq("user", loggedUser)).list();
	}

	public void deleteAreaOfInterest(AreaOfInterest selectedAreaOfInterest) {
		getSession().createSQLQuery("delete from area_of_interest where id = :id") 
	    .setParameter("id", selectedAreaOfInterest.getId())
	    .executeUpdate();
	}
	
	
}
